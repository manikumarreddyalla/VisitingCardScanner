package com.minorproject.cardscannerai.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.minorproject.cardscannerai.data.model.SyncStatus

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromMap(map: Map<String, String>?): String? {
        return if (map == null) null else gson.toJson(map)
    }

    @TypeConverter
    fun toMap(json: String?): Map<String, String>? {
        if (json.isNullOrEmpty()) return emptyMap()
        val type = object : TypeToken<Map<String, String>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun syncStatusToString(status: SyncStatus): String = status.name

    @TypeConverter
    fun stringToSyncStatus(value: String): SyncStatus = SyncStatus.valueOf(value)
}
