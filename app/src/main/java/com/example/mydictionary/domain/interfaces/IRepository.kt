package com.example.mydictionary.domain.interfaces

import com.example.mydictionary.model.retrofit.beans.RFWordTranslations
import com.example.mydictionary.model.room.RoomCard
import com.example.mydictionary.model.room.RoomWordTranslation
import io.reactivex.rxjava3.core.Single

interface IRepository {
    fun getCards(): Single<List<RoomCard>>
    fun saveCard(card:RoomCard):Single<Long>
    fun getWordTranslations(cardUid: Long): Single<List<RoomWordTranslation>>
    fun saveWordTranslations(wordTranslations: List<RoomWordTranslation>):Single<List<Long>>
    fun getTranslationsWithImage(word:String): Single<RFWordTranslations>
}