package com.example.mydictionary.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ImageDao {
    @Query("SELECT * FROM images WHERE card_uid = :cardUid")
    fun getImages(cardUid:Long): List<RoomImage>

    @Insert
    fun insert(list: List<RoomImage>):List<Long>
}