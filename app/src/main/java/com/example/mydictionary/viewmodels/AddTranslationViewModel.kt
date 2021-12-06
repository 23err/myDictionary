package com.example.mydictionary.viewmodels

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.WordTranslation
import com.example.mydictionary.domain.interfaces.IScreens
import com.example.mydictionary.interactors.RepositoryInteractor
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject

class AddTranslationViewModel @Inject constructor(
    private val screens: IScreens,
    private val router: Router,
    private val repositoryInteractor: RepositoryInteractor,
    private val mainScheduler: Scheduler,
) : BaseViewModel<List<WordTranslation>>() {
    private var card: Card? = null
    private var wordTranslations: List<WordTranslation>? = null

    fun init(card: Card) {
        this.card = card
        compositeDisposable.add(
            repositoryInteractor.getTranslationsWithImage(card.value)
                .doOnSubscribe { liveDataForViewToObserve.postValue(AppState.Loading<List<WordTranslation>>()) }
                .observeOn(mainScheduler)
                .subscribe({ list ->
                    val state = AppState.Success<List<WordTranslation>>(list)
                    liveDataForViewToObserve.postValue(state)
                    wordTranslations = list
                }, {
                    val state = AppState.Error<List<WordTranslation>>(RuntimeException(it))
                    liveDataForViewToObserve.postValue(state)
                }
                )
        )
    }

    fun nextClick(translation: String) {
        card?.let { card ->
            if (translation.isNotEmpty()) {
                card.wordTranslations.add(WordTranslation(value = translation))
            }
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