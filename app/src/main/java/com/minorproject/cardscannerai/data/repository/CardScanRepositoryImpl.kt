package com.minorproject.cardscannerai.data.repository

import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import com.minorproject.cardscannerai.ai.ImagePreprocessor
import com.minorproject.cardscannerai.ai.NlpParser
import com.minorproject.cardscannerai.ai.OcrEngine
import com.minorproject.cardscannerai.data.dao.ScannedCardDao
import com.minorproject.cardscannerai.data.model.ScannedCardEntity
import com.minorproject.cardscannerai.data.model.SyncStatus
import com.minorproject.cardscannerai.data.sync.CrmSyncService
import com.minorproject.cardscannerai.data.sync.SyncManager
import com.minorproject.cardscannerai.domain.model.Contact
import com.minorproject.cardscannerai.domain.repository.CardScanRepository
import java.util.UUID

/**
 * Card Scan Repository with Offline-First Sync
 * 
 * Features:
 * - Lightweight Surya OCR (no ML Kit overhead)
 * - Regex-based field extraction (fast, deterministic)
 * - Local SQLite persistence with sync tracking
 * - Automatic PowerSync to CRM when online
 */
class CardScanRepositoryImpl(
    private val appContext: Context,
    private val ocrEngine: OcrEngine,
    private val nlpParser: NlpParser,
    private val imagePreprocessor: ImagePreprocessor,
    private val cardDao: ScannedCardDao,
    private val syncManager: SyncManager,
    private val crmSyncService: CrmSyncService
) : CardScanRepository {
    
    private val gson = Gson()

    override suspend fun processCardImage(uri: Uri): Contact {
        return processCardImages(listOf(uri))
    }

    override suspend fun processCardImages(imageUris: List<Uri>): Contact {
        // Step 1: OCR - Extract text using lightweight Surya
        val rawText = extractText(imageUris)

        // Step 2: NLP - Parse with regex (fast, lightweight)
        val contact = nlpParser.parse(rawText)

        // Step 3: Local Persistence - Save to SQLite with sync status
        persistToLocalDatabase(contact, rawText)

        // Step 4: Queue for sync - Will auto-sync when online
        queueForSync(contact)

        return contact
    }

    private suspend fun extractText(imageUris: List<Uri>): String {
        val allUris = mutableListOf<Uri>()
        for (uri in imageUris) {
            allUris.add(uri)
            try {
                allUris.add(imagePreprocessor.enhanceForOcr(appContext, uri))
            } catch (e: Exception) {
                // If enhancement fails, just use original
            }
        }
        return ocrEngine.extractBestText(appContext, allUris)
    }

    private suspend fun persistToLocalDatabase(contact: Contact, rawText: String) {
        val fieldsJson = gson.toJson(mapOf(
            "name" to contact.name?.value,
            "email" to contact.email?.value,
            "phone" to contact.phone?.value,
            "company" to contact.company?.value,
            "designation" to contact.designation?.value,
            "address" to contact.address?.value,
            "website" to contact.website?.value,
            "category" to contact.category
        ))

        val entity = ScannedCardEntity(
            id = UUID.randomUUID().toString(),
            originalText = rawText,
            fieldsJson = fieldsJson,
            syncStatus = SyncStatus.PENDING
        )

        cardDao.insert(entity)
    }

    private suspend fun queueForSync(contact: Contact) {
        // If online, trigger immediate sync
        // If offline, will sync automatically when connection restored
        if (syncManager.isNetworkAvailable()) {
            syncManager.triggerSync()
        }
    }
}
