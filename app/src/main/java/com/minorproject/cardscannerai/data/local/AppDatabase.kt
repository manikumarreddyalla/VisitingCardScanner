package com.minorproject.cardscannerai.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.minorproject.cardscannerai.data.db.Converters
import com.minorproject.cardscannerai.data.local.dao.ContactDao
import com.minorproject.cardscannerai.data.local.entity.ContactEntity
import com.minorproject.cardscannerai.data.dao.ScannedCardDao
import com.minorproject.cardscannerai.data.model.ScannedCardEntity

@Database(
    entities = [ContactEntity::class, ScannedCardEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
    abstract fun scannedCardDao(): ScannedCardDao
}
