package com.example.mydictionary.interactors

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.WordTranslation
import com.example.mydictionary.domain.interfaces.IRepository
import com.example.mydictionary.domain.interfaces.Mapper
import com.example.mydictionary.model.retrofit.beans.RFWordTranslations
import com.example.mydictionary.model.room.RoomCard
import com.example.mydictionary.model.room.RoomWordTranslation
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RepositoryInteractor @Inject constructor(
    private val repo: IRepository,
    private val cardMapper: Mapper<RoomCard, Card>,
    private val wordTranslationsMapper: Mapper<RoomWordTranslation, WordTranslation>
) {
    fun getCards(): Single<List<Card>> =
        repo.getCards().observeOn(Schedulers.computation()).map { list ->
            list.map {
                cardMapper.map(it)
            }
        }

    fun saveCard(card: Card) = repo.saveCard(cardMapper.reverseMap(card))

    fun saveTranslationWords(uid: Long, wordTranslations: MutableList<WordTranslation>) =
        repo.saveWordTranslations(wordTranslations.map { wordTranslationsMapper.reverseMap(it) })

    fun getCards(word:String): Single<RFWordTranslations> {
        return repo.getCards(word)
    }
}