package com.example.mydictionary.presenters

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.WordTranslation
import com.example.mydictionary.domain.interfaces.AddTranslationView
import com.example.mydictionary.domain.interfaces.IScreens
import com.example.mydictionary.interactors.RepositoryInteractor
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import javax.inject.Inject

class AddTranslationPresenter @Inject constructor(
    private val screens: IScreens,
    private val router: Router,
    private val repositoryInteractor: RepositoryInteractor,
    private val mainScheduler: Scheduler,
) : MvpPresenter<AddTranslationView>() {
    private var card: Card? = null
    private var wordTranslations: List<WordTranslation>? = null

    fun init(card: Card) {
        this.card = card
        viewState.setTitle(card.value)
        repositoryInteractor.getTranslationsWithImage(card.value).observeOn(mainScheduler)
            .subscribe { list ->
                wordTranslations = list
                list.forEach {
                    viewState.addTranslationWord(it.id ?: 0, it.value)
                }
            }

    }

    fun nextClick(translation: String) {
        card?.let { card ->
            card.wordTranslations.add(WordTranslation(value = translation))
            router.navigateTo(screens.addImage(card))
        }
    }

    fun checkedWord(id: Int, checked: Boolean) {

        wordTranslations?.let { it ->
            val wordTranslation = it.first { it.id == id }
            if (checked) {
                card?.wordTranslations?.add(wordTranslation)
            } else {
                card?.wordTranslations?.first { it.id == id }?.let {
                    card?.wordTranslations?.remove(it)
                }
            }
        }
    }
}