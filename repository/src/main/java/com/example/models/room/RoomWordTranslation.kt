package com.example.mydictionary.models.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_translations")
data class RoomWordTranslation(
    @PrimaryKey(autoGenerate = true)
    var uid: Long? = null,
    var value: String,
    @ColumnInfo(name = "card_uid") var cardUid: Long,
)
