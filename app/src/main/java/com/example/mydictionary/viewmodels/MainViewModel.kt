package com.example.mydictionary.viewmodels

import com.example.mydictionary.domain.interfaces.IScreens
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val router: Router,
    private val screens:IScreens,
) {

    fun init() {
        router.navigateTo(screens.wordList())
    }
}
