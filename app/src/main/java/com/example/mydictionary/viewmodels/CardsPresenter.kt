package com.example.mydictionary.viewmodels

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.interfaces.CardsView
import com.example.mydictionary.domain.interfaces.IRVPresenter
import com.example.mydictionary.domain.interfaces.IScreens
import com.example.mydictionary.domain.interfaces.IWordView
import com.example.mydictionary.interactors.RepositoryInteractor
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.*
import moxy.MvpPresenter

class CardsPresenter(
    private val repositoryInteractor: RepositoryInteractor,
    val wordsPresenter: IRVPresenter<Card, IWordView>,
    private val router: Router,
    private val screens: IScreens

) : MvpPresenter<CardsView>() {
    private val presenterCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler{ _, throwable->
            handleError(throwable)
        }
    )

    private fun handleError(throwable: Throwable) {
        throwable.message?.let{
            viewState.showError(it)
        }
    }

    fun init() {
        wordsPresenter.onClickListener = { pos ->
            router.navigateTo(screens.card(wordsPresenter.list.get(pos)))
        }

        presenterCoroutineScope.launch {
            val cardsList = repositoryInteractor.getCards()
            wordsPresenter.list.apply {
                clear()
                addAll(cardsList)
                viewState.notifyDataSetChanged()
            }
        }
    }

    fun addWordClick() {
        router.navigateTo(screens.addWord())
    }

    class WordsListPresenter() : IRVPresenter<Card, IWordView> {
        override val list: MutableList<Card> = mutableListOf<Card>()
        override var onClickListener: ((pos: Int) -> Unit)? = null

        override fun onBind(itemView: IWordView, position: Int) {
            val card = list[position]
            itemView.setLabel(card.value)
            card.imageUrl?.let {
                itemView.setImage(it)
            }
        }

        override fun getItemCount() = list.size
    }
}