package com.example.mydictionary.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WordTranslationDao {
    @Query("SELECT * FROM word_translations WHERE card_uid = :cardUid")
    fun get(cardUid: Long): List<RoomWordTranslation>

    @Insert
    fun insert(wordTranslation: RoomWordTranslation): Long

    @Insert
    fun insertAll(listWordTranslation: List<RoomWordTranslation>):List<Long>
}