package com.minorproject.cardscannerai.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.minorproject.cardscannerai.data.local.entity.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts ORDER BY updatedAt DESC")
    fun observeAll(): Flow<List<ContactEntity>>

    @Query("SELECT * FROM contacts WHERE (name LIKE '%' || :query || '%' OR company LIKE '%' || :query || '%' OR phone LIKE '%' || :query || '%' OR email LIKE '%' || :query || '%') ORDER BY updatedAt DESC")
    fun observeFiltered(query: String): Flow<List<ContactEntity>>

    @Query("SELECT * FROM contacts WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): ContactEntity?

    @Query("SELECT * FROM contacts")
    suspend fun getAllOnce(): List<ContactEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ContactEntity): Long

    @Update
    suspend fun update(entity: ContactEntity)

    @Delete
    suspend fun delete(entity: ContactEntity)

    @Query("DELETE FROM contacts WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM contacts WHERE cloudSynced = 0")
    suspend fun unsynced(): List<ContactEntity>

    @Query("UPDATE contacts SET cloudSynced = 1 WHERE id = :id")
    suspend fun markSynced(id: Long)
}
