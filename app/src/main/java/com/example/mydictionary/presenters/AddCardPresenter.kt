package com.example.mydictionary.presenters

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.interfaces.AddCardView
import com.example.mydictionary.domain.interfaces.IScreens
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import javax.inject.Inject

class AddCardPresenter @Inject constructor(
    private val screens:IScreens,
    private val router: Router,
) : MvpPresenter<AddCardView>() {
    fun nextClicked(word: String) {
        router.navigateTo(screens.addTranslation(Card(value=word)))
    }
}