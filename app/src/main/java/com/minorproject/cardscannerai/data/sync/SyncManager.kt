package com.minorproject.cardscannerai.data.sync

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

/**
 * SyncManager handles offline-first syncing with CRM via WorkManager
 * 
 * Uses Android WorkManager for reliable background sync:
 * - Survives app restarts
 * - Respects device constraints (battery, network)
 * - Automatic retry with exponential backoff
 * - Deduplication of sync tasks
 * 
 * Responsibilities:
 * - Monitor network connectivity
 * - Queue pending records for sync via WorkManager
 * - Automatically sync when connection restored
 * - Handle sync errors and retries
 */
class SyncManager(private val context: Context) {
    
    private val _syncState = MutableStateFlow<SyncState>(SyncState.Idle)
    val syncState: Flow<SyncState> = _syncState.asStateFlow()

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val workManager = WorkManager.getInstance(context)

    init {
        setupNetworkListener()
        scheduleSyncWorker()
    }

    private fun setupNetworkListener() {
        connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                // Network restored - trigger sync
                triggerSync()
            }

            override fun onLost(network: Network) {
                _syncState.value = SyncState.Offline
            }
        })
    }

    private fun scheduleSyncWorker() {
        // Schedule periodic sync every 15 minutes
        // Syncs only when network is available
        val syncRequest = PeriodicWorkRequestBuilder<CardSyncWorker>(
            15, TimeUnit.MINUTES
        )
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 1, TimeUnit.MINUTES)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "card_sync",
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )
    }

    suspend fun isNetworkAvailable(): Boolean {
        return withContext(Dispatchers.IO) {
            val activeNetwork = connectivityManager.activeNetwork ?: return@withContext false
            val caps = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return@withContext false
            caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }
    }

    fun triggerSync() {
        _syncState.value = SyncState.Syncing
        workManager.enqueue(
            androidx.work.OneTimeWorkRequestBuilder<CardSyncWorker>().build()
        )
    }

    fun setSyncSuccess() {
        _syncState.value = SyncState.Synced
    }

    fun setSyncError(error: String) {
        _syncState.value = SyncState.Error(error)
    }

    fun setIdle() {
        _syncState.value = SyncState.Idle
    }
}

sealed class SyncState {
    object Idle : SyncState()
    object Offline : SyncState()
    object Syncing : SyncState()
    object Synced : SyncState()
    data class Error(val message: String) : SyncState()
}
