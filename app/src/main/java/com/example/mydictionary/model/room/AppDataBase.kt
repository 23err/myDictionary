package com.example.mydictionary.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomCard::class, RoomWordTranslation::class], version = 2)
abstract class AppDataBase : RoomDatabase() {
    abstract fun cardsDao(): CardDao
    abstract fun wordtranslationsDao(): WordTranslationDao
}