package com.example.mydictionary.presenters

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.interfaces.CardsView
import com.example.mydictionary.domain.interfaces.IRVPresenter
import com.example.mydictionary.domain.interfaces.IScreens
import com.example.mydictionary.domain.interfaces.IWordView
import com.example.mydictionary.interactors.RepositoryInteractor
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import javax.inject.Inject

class CardsPresenter @Inject constructor(
    private val repositoryInteractor: RepositoryInteractor,
    private val mainScheduler: Scheduler,
    val wordsPresenter: IRVPresenter<Card, IWordView>,
    private val router: Router,
    private val screens: IScreens,
) : MvpPresenter<CardsView>() {

    private val compositeDisposable = CompositeDisposable()

    fun init() {
        wordsPresenter.onClickListener = { pos ->
            router.navigateTo(screens.card(wordsPresenter.list.get(pos)))
        }
        compositeDisposable.add(repositoryInteractor.getCards().observeOn(mainScheduler).subscribe { cardsList ->
            wordsPresenter.list.apply {
                clear()
                addAll(cardsList)
                viewState.notifyDataSetChanged()
            }
        })
    }

    fun addWordClick() {
        router.navigateTo(screens.addWord())
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    class WordsListPresenter() : IRVPresenter<Card, IWordView> {
        override val list: MutableList<Card> = mutableListOf<Card>()
        override var onClickListener: ((pos: Int) -> Unit)? = null

        override fun onBind(itemView: IWordView, position: Int) {
            val card = list[position]
            itemView.setLabel(card.value)
            card.imageUrl?.let{
                itemView.setImage(it)
            }

        }

        override fun getItemCount() = list.size
    }
}