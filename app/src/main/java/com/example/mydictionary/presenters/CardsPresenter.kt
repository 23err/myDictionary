package com.example.mydictionary.presenters

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.interfaces.IRepository
import com.example.mydictionary.domain.interfaces.IScreens
import com.example.mydictionary.domain.interfaces.CardsView
import com.example.mydictionary.views.adapters.IRVPresenter
import com.example.mydictionary.views.adapters.IWordView
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import javax.inject.Inject

class CardsPresenter @Inject constructor(
    private val repository: IRepository,
    private val mainScheduler: Scheduler,
    val wordsPresenter: IRVPresenter,
    private val router: Router,
    private val screens:IScreens,
) : MvpPresenter<CardsView>() {
    fun init() {
        repository.getWords().observeOn(mainScheduler).subscribe { wordsList ->
            wordsPresenter.list.apply {
                clear()
                addAll(wordsList)
                viewState.notifyDataSetChanged()
            }
        }
    }

    fun addWordClick() {
        router.navigateTo(screens.addWord())
    }

    class WordsListPresenter() : IRVPresenter {
        override val list: MutableList<Card> = mutableListOf<Card>()
        override var onClickListener: ((pos: Int) -> Unit)? = null

        override fun onBind(itemView: IWordView, position: Int) {
            itemView.setLabel(list[position].value)
        }

        override fun getItemCount() = list.size
    }
}