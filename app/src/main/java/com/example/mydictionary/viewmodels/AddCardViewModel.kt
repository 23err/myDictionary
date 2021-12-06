package com.example.mydictionary.viewmodels

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.interfaces.IScreens
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class AddCardViewModel @Inject constructor(
    private val screens:IScreens,
    private val router: Router,
) {
    fun nextClicked(word: String) {
        router.navigateTo(screens.addTranslation(Card(value=word)))
    }
}