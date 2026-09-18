package com.minorproject.cardscannerai.data.repository

import com.minorproject.cardscannerai.data.local.dao.ContactDao
import com.minorproject.cardscannerai.data.mapper.toDomain
import com.minorproject.cardscannerai.data.mapper.toEntity
import com.minorproject.cardscannerai.data.prefs.SettingsStore
import com.minorproject.cardscannerai.data.remote.FirebaseSyncDataSource
import com.minorproject.cardscannerai.domain.model.Contact
import com.minorproject.cardscannerai.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ContactRepositoryImpl(
    private val dao: ContactDao,
    private val settingsStore: SettingsStore,
    private val syncDataSource: FirebaseSyncDataSource
) : ContactRepository {

    override fun observeContacts(query: String): Flow<List<Contact>> {
        return (if (query.isBlank()) dao.observeAll() else dao.observeFiltered(query))
            .map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun getContact(id: Long): Contact? {
        return dao.getById(id)?.toDomain()
    }

    override suspend fun saveContact(contact: Contact): Long {
        val duplicate = findDuplicate(contact)
        val updated = contact.copy(
            duplicateOfId = duplicate?.id,
            updatedAt = System.currentTimeMillis()
        )
        val id = dao.insert(updated.toEntity(cloudSynced = false))
        if (isCloudSyncEnabledSnapshot()) {
            syncPendingContacts()
        }
        return id
    }

    override suspend fun deleteContact(id: Long) {
        dao.deleteById(id)
    }

    override suspend fun findDuplicate(contact: Contact): Contact? {
        return dao.getAllOnce()
            .map { it.toDomain() }
            .firstOrNull {
                (contact.phone?.value != null && it.phone?.value == contact.phone.value) ||
                    (contact.email?.value != null && it.email?.value == contact.email.value)
            }
    }

    override suspend fun syncPendingContacts() {
        if (!isCloudSyncEnabledSnapshot()) return
        val pending = dao.unsynced()
        pending.forEach { entity ->
            runCatching {
                syncDataSource.syncContact(entity)
                dao.markSynced(entity.id)
            }
        }
    }

    override suspend fun setCloudSyncEnabled(enabled: Boolean) {
        settingsStore.setCloudSyncEnabled(enabled)
        if (enabled) syncPendingContacts()
    }

    override fun isCloudSyncEnabled(): Flow<Boolean> = settingsStore.cloudSyncEnabled()

    private fun isCloudSyncEnabledSnapshot(): Boolean {
        return settingsStore.cloudSyncEnabledSnapshot()
    }
}
