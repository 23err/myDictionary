package com.example.mydictionary.viewmodels

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.Image
import com.example.mydictionary.domain.interfaces.CardView
import com.example.mydictionary.domain.interfaces.IImageItemView
import com.example.mydictionary.domain.interfaces.IRVPresenter
import com.example.mydictionary.interactors.RepositoryInteractor
import kotlinx.coroutines.*
import moxy.MvpPresenter

class CardPresenter (
    val rvPresenter: IRVPresenter<Image, IImageItemView>,
    private val repositoryInteractor: RepositoryInteractor,
) : MvpPresenter<CardView>() {

    private var translation: String? = null
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

    fun init(card: Card) {
        viewState.setTitle(card.value)

        card.uid?.let {
            presenterCoroutineScope.launch {
                val translation = repositoryInteractor.getWordTranslations(it)
                    .map { it.value }
                    .joinToString(separator = ", ")
                val imageList = repositoryInteractor.getImages(it)
                rvPresenter.list.apply {
                    clear()
                    addAll(imageList)
                    viewState.notifyDataSetChanged()
                }
            }
        }
    }

    fun showTranslateClicked() {
        translation?.let {
            viewState.showTranslation(it)
        }
    }

    override fun onDestroy() {
        presenterCoroutineScope.cancel()
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