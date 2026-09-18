package com.minorproject.cardscannerai.data.prefs

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SettingsStore(context: Context) {
    private val prefs = context.getSharedPreferences("card_scanner_settings", Context.MODE_PRIVATE)
    private val cloudSyncFlow = MutableStateFlow(prefs.getBoolean(KEY_CLOUD_SYNC, false))

    fun cloudSyncEnabled(): Flow<Boolean> = cloudSyncFlow

    fun cloudSyncEnabledSnapshot(): Boolean = cloudSyncFlow.value

    suspend fun setCloudSyncEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_CLOUD_SYNC, enabled).apply()
        cloudSyncFlow.value = enabled
    }

    companion object {
        private const val KEY_CLOUD_SYNC = "cloud_sync_enabled"
    }
}
