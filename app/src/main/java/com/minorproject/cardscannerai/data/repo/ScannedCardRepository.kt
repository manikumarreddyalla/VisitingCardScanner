package com.minorproject.cardscannerai.data.repo

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import com.minorproject.cardscannerai.ai.FieldMapper
import com.minorproject.cardscannerai.data.local.AppDatabase
import com.minorproject.cardscannerai.data.model.ScannedCardEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class ScannedCardRepository(
    private val db: AppDatabase,
    private val gson: Gson,
    private val fieldMapper: FieldMapper
) {
    private val dao = db.scannedCardDao()

    suspend fun insertScannedText(originalText: String, fields: Map<String, String>) {
        val id = UUID.randomUUID().toString()
        val entity = ScannedCardEntity(
            id = id,
            originalText = originalText,
            fieldsJson = gson.toJson(fields)
        )
        dao.insert(entity)
    }

    fun getAll(): Flow<List<ScannedCardEntity>> = dao.getAll()

    suspend fun mapFieldsFromText(text: String): Map<String, String> {
        return fieldMapper.mapFieldsFromText(text)
    }

    suspend fun exportToVCard(id: String): String? {
        val list = dao.getAll().first()
        val entity = list.firstOrNull { it.id == id } ?: return null
        val type = object : TypeToken<Map<String, String>>() {}.type
        val fieldsMap: Map<String, String> = try {
            gson.fromJson(entity.fieldsJson, type) ?: emptyMap()
        } catch (e: Exception) {
            emptyMap()
        }

        val name = fieldsMap["name"].orEmpty()
        val email = fieldsMap["email"].orEmpty()

        val vcard = buildString {
            appendLine("BEGIN:VCARD")
            appendLine("VERSION:3.0")
            if (name.isNotBlank()) appendLine("FN:$name")
            if (email.isNotBlank()) appendLine("EMAIL:$email")
            appendLine("END:VCARD")
        }
        return vcard
    }
}
