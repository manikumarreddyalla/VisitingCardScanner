package com.minorproject.cardscannerai.domain.model

data class Contact(
    val id: Long = 0,
    val name: ExtractedField? = null,
    val phone: ExtractedField? = null,
    val email: ExtractedField? = null,
    val company: ExtractedField? = null,
    val designation: ExtractedField? = null,
    val address: ExtractedField? = null,
    val website: ExtractedField? = null,
    val category: ContactCategory = ContactCategory.UNKNOWN,
    val rawText: String = "",
    val duplicateOfId: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
