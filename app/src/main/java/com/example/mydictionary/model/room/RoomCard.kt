package com.example.mydictionary.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class RoomCard(
    @PrimaryKey(autoGenerate = true)
    var uid: Long? = null,
    var value: String,
    @ColumnInfo(name = "image_url") var imageUrl: String? = null,
)