package com.minorproject.cardscannerai.ai

import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.minorproject.cardscannerai.domain.model.Contact
import com.minorproject.cardscannerai.domain.model.ContactCategory
import com.minorproject.cardscannerai.domain.model.ExtractedField

class NlpParser {
    private val phoneUtil = PhoneNumberUtil.getInstance()

    private val emailRegex = Regex("[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
    private val phoneRegex = Regex("(\\+?\\d[\\d\\s().-]{7,}\\d)")
    private val websiteRegex = Regex("((https?://)?(www\\.)?[A-Za-z0-9.-]+\\.[A-Za-z]{2,})")

    private val designationHints = setOf(
        "ceo", "cto", "founder", "manager", "director", "engineer", "developer", "analyst", "lead", "owner", "consultant", "head"
    )

    private val addressHints = setOf("road", "street", "st", "ave", "avenue", "nagar", "city", "state", "zip", "india")
    private val companyHints = setOf("inc", "llc", "ltd", "corp", "company", "solutions", "tech", "studio", "group", "systems")

    fun parse(rawText: String): Contact {
        val lines = normalizeLines(rawText)

        val email = lines.firstNotNullOfOrNull { line ->
            emailRegex.find(line)?.value
        }?.let {
            ExtractedField(it, if (isValidEmail(it)) 0.95f else 0.4f)
        }

        val website = lines.firstNotNullOfOrNull { line ->
            websiteRegex.find(line)?.value
        }?.let { ExtractedField(normalizeWebsite(it), 0.75f) }

        val phone = lines.firstNotNullOfOrNull { line ->
            phoneRegex.find(line)?.value
        }?.let { candidate ->
            val formatted = formatPhone(candidate)
            val confidence = if (formatted != candidate) 0.9f else 0.7f
            ExtractedField(formatted, confidence)
        }

        val name = detectName(lines, email?.value, website?.value, phone?.value)
        val company = detectCompany(lines, email?.value, website?.value, name?.value)
        val designation = detectDesignation(lines)
        val address = detectAddress(lines)
        val category = detectCategory(lines, designation?.value, company?.value)

        return Contact(
            name = name,
            phone = phone,
            email = email,
            company = company,
            designation = designation,
            address = address,
            website = website,
            category = category,
            rawText = rawText
        )
    }

    private fun detectName(lines: List<String>, email: String?, website: String?, phone: String?): ExtractedField? {
        val honorifics = setOf("mr", "mrs", "ms", "miss", "dr", "prof", "sir")

        data class Candidate(val line: String, val score: Double)

        val candidates = mutableListOf<Candidate>()

        for ((idx, raw) in lines.withIndex()) {
            var line = raw.trim()
            if (line.isBlank()) continue
            val lower = line.lowercase()

            // skip obvious non-name lines
            if (line.contains("@")) continue
            if (phoneRegex.containsMatchIn(line)) continue
            if (website?.let { lower.contains(it.lowercase()) } == true) continue
            if (addressHints.any { lower.contains(it) }) continue
            if (designationHints.any { lower.contains(it) }) continue

            val words = line.split(Regex("\\s+"))
                .filter { it.isNotBlank() }

            if (words.isEmpty() || words.size > 4) continue
            if (words.any { it.any(Char::isDigit) }) continue

            // remove leading honorifics
            val cleanWords = if (words.firstOrNull()?.lowercase()?.trimEnd('.') in honorifics) words.drop(1) else words
            if (cleanWords.isEmpty() || cleanWords.size > 4) continue

            // capitalization heuristic
            val capCount = cleanWords.count { it.firstOrNull()?.isUpperCase() == true }
            val capScore = capCount.toDouble() / cleanWords.size

            // position: earlier lines are likelier to contain the name
            val positionScore = (lines.size - idx).toDouble() / lines.size

            // length penalty for very long lines
            val lengthScore = 1.0 - (line.length.toDouble() / 80.0).coerceIn(0.0, 0.8)

            var score = (capScore * 2.0) + positionScore + lengthScore

            // boost if email local part matches any name token
            val emailLocalParts = email?.substringBefore("@")?.split('.', '_', '-')?.map { it.lowercase() } ?: emptyList()
            if (emailLocalParts.isNotEmpty()) {
                val matches = cleanWords.count { w -> emailLocalParts.any { it == w.lowercase() } }
                if (matches > 0) score += 1.0
            }

            candidates.add(Candidate(cleanWords.joinToString(" "), score))
        }

        val best = candidates.maxByOrNull { it.score } ?: return null

        val confidence = when {
            best.score > 3.0 -> 0.92f
            best.score > 2.0 -> 0.82f
            best.score > 1.5 -> 0.72f
            else -> 0.6f
        }

        return ExtractedField(best.line, confidence)
    }

    private fun detectCompany(lines: List<String>, email: String?, website: String?, name: String?): ExtractedField? {
        val domainBased = email?.substringAfter("@")?.substringBeforeLast('.')
            ?.replaceFirstChar { it.uppercase() }
        if (!domainBased.isNullOrBlank() && domainBased.length > 2) {
            return ExtractedField(domainBased, 0.85f)
        }

        val webCompany = website?.replace("https://", "")?.replace("http://", "")?.replace("www.", "")
            ?.substringBefore('.')
            ?.replaceFirstChar { it.uppercase() }
        if (!webCompany.isNullOrBlank() && webCompany.length > 2) {
            return ExtractedField(webCompany, 0.8f)
        }

        val fallback = lines.firstOrNull { line ->
            val lower = line.lowercase()
            line.any(Char::isUpperCase) &&
                line.length > 4 &&
                line != name &&
                companyHints.any { lower.contains(it) } ||
                (line.count(Char::isUpperCase) >= 3 && line != name)
        }
        return fallback?.let { ExtractedField(it, 0.62f) }
    }

    private fun detectDesignation(lines: List<String>): ExtractedField? {
        val line = lines.firstOrNull { l -> designationHints.any { l.lowercase().contains(it) } } ?: return null
        return ExtractedField(line, 0.84f)
    }

    private fun detectAddress(lines: List<String>): ExtractedField? {
        val line = lines.firstOrNull { l ->
            val lower = l.lowercase()
            addressHints.any { lower.contains(it) } || (l.count(Char::isDigit) >= 2 && l.contains(","))
        } ?: return null
        return ExtractedField(line, 0.74f)
    }

    private fun detectCategory(lines: List<String>, designation: String?, company: String?): ContactCategory {
        val text = (lines + listOfNotNull(designation, company)).joinToString(" ").lowercase()
        return when {
            text.contains("vendor") || text.contains("supplier") -> ContactCategory.VENDOR
            text.contains("client") || text.contains("customer") -> ContactCategory.CLIENT
            text.contains("friend") || text.contains("personal") -> ContactCategory.PERSONAL
            company != null || designation != null -> ContactCategory.BUSINESS
            else -> ContactCategory.UNKNOWN
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return emailRegex.matches(email)
    }

    private fun normalizeLines(rawText: String): List<String> {
        return rawText.lines()
            .map { it.replace("|", "I").replace("~", "-").trim() }
            .map { line -> line.replace(Regex("\\s+"), " ") }
            .filter { it.isNotBlank() }
    }

    private fun formatPhone(phone: String): String {
        return try {
            val parsed = phoneUtil.parse(phone, "IN")
            phoneUtil.format(parsed, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
        } catch (_: Exception) {
            phone.filter { it.isDigit() || it == '+' }
        }
    }

    private fun normalizeWebsite(value: String): String {
        return if (value.startsWith("http")) value else "https://$value"
    }
}
