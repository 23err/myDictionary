package com.example.mydictionary.viewmodels

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.Image
import com.example.mydictionary.domain.interfaces.AddImageView
import com.example.mydictionary.domain.interfaces.IRVPresenter
import com.example.mydictionary.domain.interfaces.IScreens
import com.example.mydictionary.domain.interfaces.ISelectedImageItemView
import com.example.mydictionary.interactors.RepositoryInteractor
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.*
import moxy.MvpPresenter

class AddImagePresenter(
    private val repositoryInteractor: RepositoryInteractor,
    private val router: Router,
    private val screens: IScreens,
    val rvImagesPresenter: IRVPresenter<Image, ISelectedImageItemView>,
) : MvpPresenter<AddImageView>() {
    private var card: Card? = null
    private val listOfImages = mutableListOf<Image>()
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
        this.card = card
        viewState.setTitle(card.value)
        val images = card.wordTranslations.filter { it.image != null }.map { it.image!! }
        rvImagesPresenter.onClickListener = { pos ->
            val image = rvImagesPresenter.list.get(pos)
            if (listOfImages.indexOf(image) == -1) {
                listOfImages.add(image)
            } else {
                listOfImages.remove(image)
            }
        }
        rvImagesPresenter.list.apply {
            clear()
            addAll(images)
            viewState.notifyDataSetChanged()
        }

    }

    fun nextClicked() {
        if (listOfImages.size > 0){
            card?.imageUrl = listOfImages[0].url
        }
        card?.let {
            presenterCoroutineScope.launch {
                val uid = repositoryInteractor.saveCard(it)
                repositoryInteractor.saveTranslationWords(uid, it.wordTranslations)
                if (listOfImages.size > 0) {
                    repositoryInteractor.saveImages(uid, listOfImages)
                }
                router.newRootScreen(screens.wordList())
            }
        }
    }

    class RVImagesPresenter : IRVPresenter<Image, ISelectedImageItemView> {
        override val list = mutableListOf<Image>()
        override var onClickListener: ((Int) -> Unit)? = null

        override fun onBind(itemView: ISelectedImageItemView, position: Int) {
            itemView.setImage(list[position].url)
        }

        override fun getItemCount() = list.size
    }

}