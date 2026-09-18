package com.minorproject.cardscannerai.domain.repository

import com.minorproject.cardscannerai.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    fun observeContacts(query: String = ""): Flow<List<Contact>>
    suspend fun getContact(id: Long): Contact?
    suspend fun saveContact(contact: Contact): Long
    suspend fun deleteContact(id: Long)
    suspend fun findDuplicate(contact: Contact): Contact?
    suspend fun syncPendingContacts()
    suspend fun setCloudSyncEnabled(enabled: Boolean)
    fun isCloudSyncEnabled(): Flow<Boolean>
}
