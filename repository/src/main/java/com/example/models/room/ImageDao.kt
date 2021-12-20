package com.example.mydictionary.models.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ImageDao {
    @Query("SELECT * FROM images WHERE card_uid = :cardUid")
    suspend fun getImages(cardUid:Long): List<RoomImage>

    @Insert
    suspend fun insert(list: List<RoomImage>):List<Long>
}