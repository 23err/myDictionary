package com.example.mydictionary.model

import com.example.mydictionary.domain.interfaces.IRepository
import com.example.mydictionary.model.room.AppDataBase
import com.example.mydictionary.model.room.RoomCard
import com.example.mydictionary.model.room.RoomWordTranslation
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class Repository @Inject constructor(
    private val db: AppDataBase
) : IRepository {
    override fun getCards() =
        Single.fromCallable { db.cardsDao().get() }.subscribeOn(Schedulers.io())

    override fun saveCard(card: RoomCard) =
        Single.fromCallable {
            db.cardsDao().insert(card)
        }.subscribeOn(Schedulers.io())

    override fun getWordTranslations(cardUid: Long): Single<List<RoomWordTranslation>> =
        Single.fromCallable {
            db.wordtranslationsDao().get(cardUid)
        }.subscribeOn(Schedulers.io())

    override fun saveWordTranslations(wordTranslations: List<RoomWordTranslation>) =
        Single.fromCallable {
            db.wordtranslationsDao().insertAll(wordTranslations)
        }.subscribeOn(Schedulers.io())

}