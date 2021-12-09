package com.example.mydictionary.viewmodels

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.WordTranslation
import com.example.mydictionary.domain.interfaces.IScreens
import com.example.mydictionary.interactors.RepositoryInteractor
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.launch

class AddTranslationViewModel (
    private val screens: IScreens,
    private val router: Router,
    private val repositoryInteractor: RepositoryInteractor,
) : BaseViewModel<List<WordTranslation>>() {
    private var card: Card? = null
    private var wordTranslations: List<WordTranslation>? = null

    fun init(card: Card) {
        this.card = card
        viewModelCoroutineScope.launch {
            val list = repositoryInteractor.getTranslationsWithImage(card.value)
            val state = AppState.Success<List<WordTranslation>>(list)
            liveDataForViewToObserve.postValue(state)
            wordTranslations = list
        }
    }

    override fun handleError(throwable: Throwable) {
        val errorState = AppState.Error<List<WordTranslation>>(throwable)
        liveDataForViewToObserve.postValue(errorState)
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