package com.example.mydictionary.presenters

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.WordTranslation
import com.example.mydictionary.domain.interfaces.AddTranslationView
import com.example.mydictionary.domain.interfaces.IScreens
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import javax.inject.Inject

class AddTranslationPresenter @Inject constructor(
    private val screens: IScreens,
    private val router: Router,
) : MvpPresenter<AddTranslationView>() {
    private var card: Card? = null
    fun init(card: Card) {
        this.card = card
        viewState.setTitle(card.value)
    }

    fun nextClick(translation: String) {
        card?.let{ card->
            card.wordTranslations?.let{ wordTranslations->
                wordTranslations.add(WordTranslation(value = translation))
            }
            router.navigateTo(screens.addImage(card))
        }
    }

}