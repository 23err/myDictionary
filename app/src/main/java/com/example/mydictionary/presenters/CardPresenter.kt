package com.example.mydictionary.presenters

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.Image
import com.example.mydictionary.domain.interfaces.CardView
import com.example.mydictionary.domain.interfaces.IImageItemView
import com.example.mydictionary.domain.interfaces.IRVPresenter
import com.example.mydictionary.interactors.RepositoryInteractor
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import javax.inject.Inject

class CardPresenter @Inject constructor(
    val rvPresenter: IRVPresenter<Image, IImageItemView>,
    private val repositoryInteractor: RepositoryInteractor,
    private val mainScheduler: Scheduler,
) : MvpPresenter<CardView>() {

    private var translation: String? = null
    private val compositeDisposable = CompositeDisposable()

    fun init(card: Card) {
        viewState.setTitle(card.value)

        card.uid?.let {
            compositeDisposable.add(repositoryInteractor.getWordTranslations(it)
                .observeOn(mainScheduler)
                .subscribe { wordTranslations ->
                    translation = wordTranslations.map { it.value }.joinToString(separator = ", ")
                })

            compositeDisposable.add(
                repositoryInteractor.getImages(it).observeOn(mainScheduler).subscribe { imageList ->
                    rvPresenter.list.apply {
                        clear()
                        addAll(imageList)
                        viewState.notifyDataSetChanged()
                    }
                })
        }
    }

    fun showTranslateClicked() {
        translation?.let {
            viewState.showTranslation(it)
        }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    class RVCardImagesPresenter : IRVPresenter<Image, IImageItemView> {
        override val list: MutableList<Image> = mutableListOf()
        override var onClickListener: ((Int) -> Unit)? = null

        override fun onBind(itemView: IImageItemView, position: Int) {
            itemView.setImage(list[position].url)
        }

        override fun getItemCount() = list.size
    }


}