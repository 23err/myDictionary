package com.example.mydictionary.presenters

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.WordTranslation
import com.example.mydictionary.domain.interfaces.AddTranslationView
import com.example.mydictionary.domain.interfaces.IRepository
import com.example.mydictionary.domain.interfaces.IScreens
import com.example.mydictionary.model.retrofit.beans.RFMeaning
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import javax.inject.Inject

class AddTranslationPresenter @Inject constructor(
    private val screens: IScreens,
    private val router: Router,
    private val repo: IRepository,
    private val mainScheduler: Scheduler,
) : MvpPresenter<AddTranslationView>() {
    private var card: Card? = null
    private var listMeanings: List<RFMeaning>? = null
    fun init(card: Card) {
        this.card = card
        viewState.setTitle(card.value)
        repo.getCards(card.value).observeOn(mainScheduler)
            .subscribe { list ->
                if (list.size > 0) {
                    listMeanings = list[0].meanings
                    listMeanings?.forEach { meaning ->
                        meaning.translation?.text?.let {
                            viewState.addTranslationWord(meaning.id, it)
                        }
                    }

                }
            }

    }

    fun nextClick(translation: String) {
        card?.let { card ->
            card.wordTranslations?.let { wordTranslations ->
                wordTranslations.add(WordTranslation(value = translation))
            }
            router.navigateTo(screens.addImage(card))
        }
    }

    fun checkedWord(id: Int, checked: Boolean) {

        listMeanings?.let { listMeanings ->
            val meaning = listMeanings.first { it.id == id }
            meaning.translation?.text?.let { translation ->
                if (checked) {
                    card?.wordTranslations?.add(WordTranslation(value = translation))
                } else {
                    card?.wordTranslations?.first { it.value == translation }?.let {
                        card?.wordTranslations?.remove(it)
                    }
                }
            }
        }

    }

}