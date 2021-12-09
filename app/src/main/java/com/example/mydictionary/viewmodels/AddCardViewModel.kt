package com.example.mydictionary.viewmodels

import androidx.lifecycle.ViewModel
import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.interfaces.IScreens
import com.github.terrakok.cicerone.Router

class AddCardViewModel (
    private val screens:IScreens,
    private val router: Router,
) : ViewModel() {
    fun nextClicked(word: String) {
        router.navigateTo(screens.addTranslation(Card(value=word)))
    }


}