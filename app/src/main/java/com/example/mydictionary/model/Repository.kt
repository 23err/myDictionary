package com.example.mydictionary.model

import com.example.mydictionary.domain.interfaces.IRepository
import com.example.mydictionary.model.retrofit.SkyEngApi
import com.example.mydictionary.model.retrofit.beans.RFWordTranslations
import com.example.mydictionary.model.room.AppDataBase
import com.example.mydictionary.model.room.RoomCard
import com.example.mydictionary.model.room.RoomImage
import com.example.mydictionary.model.room.RoomWordTranslation

class Repository(
    private val db: AppDataBase,
    private val api: SkyEngApi,
) : IRepository {

    override suspend fun getCards() = db.cardsDao().get()

    override suspend fun saveCard(card: RoomCard) =
        db.cardsDao().insert(card)

    override suspend fun getWordTranslations(cardUid: Long): List<RoomWordTranslation> =
        db.wordtranslationsDao().get(cardUid)

    override suspend fun saveWordTranslations(wordTranslations: List<RoomWordTranslation>) =
        db.wordtranslationsDao().insertAll(wordTranslations)

    override suspend fun getTranslationsWithImage(word: String): RFWordTranslations {
        return api.getTranslate(word).await()
    }

    override suspend fun getImages(cardUid: Long) =
        db.imagesDao().getImages(cardUid)

    override suspend fun saveImages(images: List<RoomImage>) =
        db.imagesDao().insert(images)
}
