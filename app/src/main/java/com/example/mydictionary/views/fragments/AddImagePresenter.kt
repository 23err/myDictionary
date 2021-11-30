package com.example.mydictionary.views.fragments

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.interfaces.AddImageView
import com.example.mydictionary.domain.interfaces.IRepository
import com.example.mydictionary.domain.interfaces.IScreens
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import javax.inject.Inject

class AddImagePresenter @Inject constructor(
    private val repository: IRepository,
    private val router: Router,
    private val screens: IScreens,
    private val mainScheduler: Scheduler
) : MvpPresenter<AddImageView>() {
    private var card: Card? = null
    fun init(card: Card) {
        this.card = card
        viewState.setTitle(card.value)
    }

    fun nextClicked() {
        card?.let {
            repository.saveWord(it).subscribeOn(mainScheduler).subscribe{_->
                router.newRootScreen(screens.wordList())
            }
        }
    }

}