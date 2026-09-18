package com.minorproject.cardscannerai.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String?,
    val nameConfidence: Float?,
    val phone: String?,
    val phoneConfidence: Float?,
    val email: String?,
    val emailConfidence: Float?,
    val company: String?,
    val companyConfidence: Float?,
    val designation: String?,
    val designationConfidence: Float?,
    val address: String?,
    val addressConfidence: Float?,
    val website: String?,
    val websiteConfidence: Float?,
    val category: String,
    val rawText: String,
    val duplicateOfId: Long?,
    val cloudSynced: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)
