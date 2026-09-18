package com.minorproject.cardscannerai.di

import android.content.Context
import androidx.room.Room
import com.minorproject.cardscannerai.ai.ImagePreprocessor
import com.minorproject.cardscannerai.ai.NlpParser
import com.minorproject.cardscannerai.ai.OcrEngine
import com.minorproject.cardscannerai.data.local.AppDatabase
import com.minorproject.cardscannerai.data.prefs.SettingsStore
import com.minorproject.cardscannerai.data.remote.FirebaseSyncDataSource
import com.minorproject.cardscannerai.data.repository.CardScanRepositoryImpl
import com.minorproject.cardscannerai.data.repository.ContactRepositoryImpl
import com.minorproject.cardscannerai.data.sync.CrmSyncService
import com.minorproject.cardscannerai.data.sync.SyncManager
import com.minorproject.cardscannerai.domain.repository.CardScanRepository
import com.minorproject.cardscannerai.domain.repository.ContactRepository

class AppContainer(private val context: Context) {
    private val database by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "card_scanner.db").build()
    }

    private val settingsStore by lazy { SettingsStore(context) }
    private val firebaseSyncDataSource by lazy { FirebaseSyncDataSource() }

    // Lightweight sync components
    private val syncManager by lazy { SyncManager(context) }
    private val crmSyncService by lazy { CrmSyncService() }

    val contactRepository: ContactRepository by lazy {
        ContactRepositoryImpl(
            dao = database.contactDao(),
            settingsStore = settingsStore,
            syncDataSource = firebaseSyncDataSource
        )
    }

    val cardScanRepository: CardScanRepository by lazy {
        CardScanRepositoryImpl(
            appContext = context,
            ocrEngine = OcrEngine(),
            nlpParser = NlpParser(),
            imagePreprocessor = ImagePreprocessor(),
            cardDao = database.scannedCardDao(),
            syncManager = syncManager,
            crmSyncService = crmSyncService
        )
    }
}
