package com.example.mydictionary.viewmodels

import androidx.lifecycle.ViewModel
import com.example.mydictionary.domain.interfaces.IScreens
import com.github.terrakok.cicerone.Router

class MainViewModel(
    private val router: Router,
    private val screens:IScreens,
) : ViewModel() {

    fun init() {
        router.navigateTo(screens.wordList())
    }
}
