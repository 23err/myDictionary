package com.example.mydictionary.model

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.interfaces.IRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class Repository : IRepository {
    private val words = mutableListOf(Card(value="Cow"), Card(value = "Cat"))
    override fun getWords() = Single.just(words).subscribeOn(Schedulers.io())
    override fun saveWord(card: Card) =
        Single.fromCallable<Unit>{
            words.add(card)
        }.subscribeOn(Schedulers.io())

}