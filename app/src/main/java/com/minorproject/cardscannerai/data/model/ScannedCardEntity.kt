package com.minorproject.cardscannerai.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class SyncStatus {
    PENDING,      // Waiting to sync to CRM
    SYNCED,       // Successfully synced
    ERROR,        // Sync failed
    OFFLINE       // Offline - will sync when connection restored
}

@Entity(tableName = "scanned_cards")
data class ScannedCardEntity(
    @PrimaryKey
    val id: String,
    val originalText: String,
    val fieldsJson: String,
    val syncStatus: SyncStatus = SyncStatus.PENDING,
    val syncError: String? = null,
    val lastSyncAttempt: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
