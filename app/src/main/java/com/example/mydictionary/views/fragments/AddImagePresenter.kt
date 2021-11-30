package com.example.mydictionary.views.fragments

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.interfaces.AddImageView
import com.example.mydictionary.domain.interfaces.IScreens
import com.example.mydictionary.interactors.RepositoryInteractor
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import javax.inject.Inject

class AddImagePresenter @Inject constructor(
    private val repositoryInteractor: RepositoryInteractor,
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
            repositoryInteractor.saveCard(it).subscribeOn(mainScheduler)
                .doFinally { router.newRootScreen(screens.wordList()) }
                .subscribe { uid ->
                    it.wordTranslations.let {
                        if (!it.isEmpty()) {
                            repositoryInteractor.saveTranslationWords(uid, it)
                                .observeOn(mainScheduler)
                                .subscribe()
                        }
                    }
                }
        }
    }

}