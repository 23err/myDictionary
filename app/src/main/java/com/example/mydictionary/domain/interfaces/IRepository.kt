package com.example.mydictionary.domain.interfaces

import com.example.mydictionary.domain.Card
import io.reactivex.rxjava3.core.Single

interface IRepository {
    fun getWords(): Single<MutableList<Card>>
    fun saveWord(card:Card):Single<Unit>
}