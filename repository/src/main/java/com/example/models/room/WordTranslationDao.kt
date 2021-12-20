package com.example.mydictionary.models.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WordTranslationDao {
    @Query("SELECT * FROM word_translations WHERE card_uid = :cardUid")
    suspend fun get(cardUid: Long): List<RoomWordTranslation>

    @Insert
    suspend fun insert(wordTranslation: RoomWordTranslation): Long

    @Insert
    suspend fun insertAll(listWordTranslation: List<RoomWordTranslation>):List<Long>
}