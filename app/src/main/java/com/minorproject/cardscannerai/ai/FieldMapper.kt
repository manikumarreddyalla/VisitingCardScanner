package com.minorproject.cardscannerai.ai

import com.minorproject.cardscannerai.ai.aiapi.AiService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.minorproject.cardscannerai.domain.model.Contact

class FieldMapper(private val aiService: AiService, private val nlpParser: NlpParser = NlpParser()) {
    private val gson = Gson()

    /**
     * Given raw OCR text, call the AI API to extract structured fields.
     * This is a lightweight prototype: you should adapt prompts and parsing
     * to the chosen AI provider's expected request/response schema.
     */
    suspend fun mapFieldsFromText(text: String): Map<String, String> = withContext(Dispatchers.IO) {
        val prompt = """
            Extract contact-like fields from the following visiting card OCR text.
            Return a JSON object mapping field names to values, e.g. {\"name\":\"...\", \"email\":\"...\"}.
            Text:
            $text
        """.trimIndent()

        val body: MutableMap<String, Any> = mutableMapOf(
            "prompt" to prompt,
            "max_tokens" to 800
        )

        val response = aiService.analyze("v1/analyze", body)
        if (!response.isSuccessful) return@withContext fallback(text)

        val map = response.body() ?: return@withContext fallback(text)

        // Attempt to find a JSON string or a 'fields' key in response
        val candidate = when {
            map.containsKey("fields") -> map["fields"]
            map.containsKey("data") -> map["data"]
            else -> map["result"]
        } ?: return@withContext fallback(text)

        // Normalize into Map<String,String>
        val resultMap = when (candidate) {
            is Map<*, *> -> candidate.entries.filter { it.key is String && it.value != null }
                .associate { it.key as String to it.value.toString() }
            is String -> {
                try {
                    val type = object : TypeToken<Map<String, String>>() {}.type
                    gson.fromJson<Map<String, String>>(candidate, type) ?: emptyMap()
                } catch (e: Exception) {
                    emptyMap()
                }
            }
            else -> emptyMap()
        }

        // If AI returned nothing useful, fallback to deterministic parser
        if (resultMap.isEmpty() || (!resultMap.containsKey("name") && !resultMap.containsKey("email") && !resultMap.containsKey("phone"))) {
            return@withContext fallback(text)
        }

        return@withContext resultMap
    }

    private fun fallback(text: String): Map<String, String> {
        val contact: Contact = nlpParser.parse(text)
        val out = mutableMapOf<String, String>()
        contact.name?.value?.let { out["name"] = it }
        contact.email?.value?.let { out["email"] = it }
        contact.phone?.value?.let { out["phone"] = it }
        contact.company?.value?.let { out["company"] = it }
        contact.designation?.value?.let { out["designation"] = it }
        contact.address?.value?.let { out["address"] = it }
        contact.website?.value?.let { out["website"] = it }
        return out
    }
}
