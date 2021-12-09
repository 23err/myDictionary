package com.example.mydictionary.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CardDao {
    @Query("SELECT * FROM cards")
    suspend fun get(): List<RoomCard>


    @Insert
    suspend fun insert(card:RoomCard): Long
}