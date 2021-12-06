package com.example.mydictionary.presenters

import com.example.mydictionary.domain.interfaces.IScreens
import com.example.mydictionary.domain.interfaces.MainView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val router: Router,
    private val screens:IScreens,
) : MvpPresenter<MainView>() {


    fun init() {
        router.navigateTo(screens.wordList())
    }
}
