package com.minorproject.cardscannerai.data.mapper

import com.minorproject.cardscannerai.data.local.entity.ContactEntity
import com.minorproject.cardscannerai.domain.model.Contact
import com.minorproject.cardscannerai.domain.model.ContactCategory
import com.minorproject.cardscannerai.domain.model.ExtractedField

fun ContactEntity.toDomain(): Contact {
    return Contact(
        id = id,
        name = name?.let { ExtractedField(it, nameConfidence ?: 0f) },
        phone = phone?.let { ExtractedField(it, phoneConfidence ?: 0f) },
        email = email?.let { ExtractedField(it, emailConfidence ?: 0f) },
        company = company?.let { ExtractedField(it, companyConfidence ?: 0f) },
        designation = designation?.let { ExtractedField(it, designationConfidence ?: 0f) },
        address = address?.let { ExtractedField(it, addressConfidence ?: 0f) },
        website = website?.let { ExtractedField(it, websiteConfidence ?: 0f) },
        category = ContactCategory.valueOf(category),
        rawText = rawText,
        duplicateOfId = duplicateOfId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Contact.toEntity(cloudSynced: Boolean = false): ContactEntity {
    return ContactEntity(
        id = id,
        name = name?.value,
        nameConfidence = name?.confidence,
        phone = phone?.value,
        phoneConfidence = phone?.confidence,
        email = email?.value,
        emailConfidence = email?.confidence,
        company = company?.value,
        companyConfidence = company?.confidence,
        designation = designation?.value,
        designationConfidence = designation?.confidence,
        address = address?.value,
        addressConfidence = address?.confidence,
        website = website?.value,
        websiteConfidence = website?.confidence,
        category = category.name,
        rawText = rawText,
        duplicateOfId = duplicateOfId,
        cloudSynced = cloudSynced,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
