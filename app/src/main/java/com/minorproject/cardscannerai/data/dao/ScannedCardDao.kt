package com.minorproject.cardscannerai.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.minorproject.cardscannerai.data.model.ScannedCardEntity
import com.minorproject.cardscannerai.data.model.SyncStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface ScannedCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(card: ScannedCardEntity)

    @Update
    suspend fun update(card: ScannedCardEntity)

    @Query("SELECT * FROM scanned_cards ORDER BY createdAt DESC")
    fun getAll(): Flow<List<ScannedCardEntity>>

    @Query("SELECT * FROM scanned_cards WHERE syncStatus = :status ORDER BY createdAt DESC")
    suspend fun getCardsByStatus(status: SyncStatus): List<ScannedCardEntity>

    @Query("SELECT * FROM scanned_cards WHERE syncStatus = 'PENDING' LIMIT :limit")
    suspend fun getPendingCards(limit: Int = 100): List<ScannedCardEntity>

    @Query("UPDATE scanned_cards SET syncStatus = :status, lastSyncAttempt = :timestamp WHERE id = :id")
    suspend fun updateSyncStatus(id: String, status: SyncStatus, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE scanned_cards SET syncStatus = 'ERROR', syncError = :error, lastSyncAttempt = :timestamp WHERE id = :id")
    suspend fun markSyncError(id: String, error: String, timestamp: Long = System.currentTimeMillis())

    @Delete
    suspend fun delete(card: ScannedCardEntity)
}
