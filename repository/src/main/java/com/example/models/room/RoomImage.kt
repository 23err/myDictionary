package com.example.mydictionary.models.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class RoomImage(
    @PrimaryKey(autoGenerate = true)
    var uid: Long? = null,
    var url: String,
    @ColumnInfo(name = "preview_url") var previewUrl: String? = null,
    @ColumnInfo(name = "card_uid") var cardUid: Long,
)
