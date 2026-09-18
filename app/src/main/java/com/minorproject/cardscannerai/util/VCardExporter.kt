package com.minorproject.cardscannerai.util

object VCardExporter {
    /**
     * Convert a map of extracted fields into a vCard 3.0 formatted string.
     * Recognized keys (case-insensitive): name, fn, given, family, org, title,
     * email, phone, tel, address, adr, url.
     */
    fun toVCard(fields: Map<String, String>, uid: String? = null): String {
        val lines = mutableListOf<String>()
        lines += "BEGIN:VCARD"
        lines += "VERSION:3.0"

        val name = fields.entries.firstOrNull { it.key.equals("name", true) || it.key.equals("fn", true) }?.value
        val given = fields.entries.firstOrNull { it.key.equals("given", true) }?.value
        val family = fields.entries.firstOrNull { it.key.equals("family", true) }?.value
        if (!name.isNullOrBlank()) {
            lines += "FN:${escape(name)}"
        } else if (!given.isNullOrBlank() || !family.isNullOrBlank()) {
            lines += "FN:${escape((given.orEmpty() + " " + family.orEmpty()).trim())}"
        }

        fields["org"]?.let { lines += "ORG:${escape(it)}" }
        fields["title"]?.let { lines += "TITLE:${escape(it)}" }

        // Email
        val email = fields.entries.firstOrNull { it.key.equals("email", true) }
        email?.let { lines += "EMAIL;TYPE=INTERNET:${escape(it.value)}" }

        // Phone
        val phone = fields.entries.firstOrNull { it.key.equals("phone", true) || it.key.equals("tel", true) }
        phone?.let { lines += "TEL;TYPE=CELL:${escape(it.value)}" }

        // Address (simple single-line)
        val address = fields.entries.firstOrNull { it.key.equals("address", true) || it.key.equals("adr", true) }
        address?.let { lines += "ADR;TYPE=WORK:;;${escape(it.value)};;;;" }

        fields["url"]?.let { lines += "URL:${escape(it)}" }

        if (!uid.isNullOrBlank()) lines += "UID:${escape(uid)}"

        lines += "END:VCARD"
        return lines.joinToString("\r\n")
    }

    private fun escape(value: String): String = value.replace("\n", " ").replace("\r", " ")
}
