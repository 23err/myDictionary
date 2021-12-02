package com.example.mydictionary.presenters

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.interfaces.CardsView
import com.example.mydictionary.domain.interfaces.IRVPresenter
import com.example.mydictionary.domain.interfaces.IScreens
import com.example.mydictionary.domain.interfaces.IWordView
import com.example.mydictionary.interactors.RepositoryInteractor
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import javax.inject.Inject

class CardsPresenter @Inject constructor(
    private val repositoryInteractor: RepositoryInteractor,
    private val mainScheduler: Scheduler,
    val wordsPresenter: IRVPresenter<Card, IWordView>,
    private val router: Router,
    private val screens:IScreens,
) : MvpPresenter<CardsView>() {
    fun init() {
        repositoryInteractor.getCards().observeOn(mainScheduler).subscribe { cardsList ->
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
            itemView.setLabel(list[position].value)
        }

        override fun getItemCount() = list.size
    }
}