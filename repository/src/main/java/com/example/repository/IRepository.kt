package com.example.repository

import com.example.mydictionary.model.retrofit.beans.RFWordTranslations
import com.example.mydictionary.model.room.RoomCard
import com.example.mydictionary.model.room.RoomImage
import com.example.mydictionary.model.room.RoomWordTranslation

interface IRepository {
    suspend fun getCards(): List<RoomCard>
    suspend fun saveCard(card: RoomCard): Long
    suspend fun getWordTranslations(cardUid: Long): List<RoomWordTranslation>
    suspend fun saveWordTranslations(wordTranslations: List<RoomWordTranslation>): List<Long>
    suspend fun getTranslationsWithImage(word: String): RFWordTranslations
    suspend fun getImages(cardUid: Long): List<RoomImage>
    suspend fun saveImages(images: List<RoomImage>): List<Long>
}