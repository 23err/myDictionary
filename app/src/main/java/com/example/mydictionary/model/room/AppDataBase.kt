package com.example.mydictionary.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Card::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun cardsDao(): CardDao
}