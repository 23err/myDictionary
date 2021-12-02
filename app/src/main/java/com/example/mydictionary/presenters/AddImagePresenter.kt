package com.example.mydictionary.presenters

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.Image
import com.example.mydictionary.domain.interfaces.AddImageView
import com.example.mydictionary.domain.interfaces.IRVPresenter
import com.example.mydictionary.domain.interfaces.IScreens
import com.example.mydictionary.domain.interfaces.ISelectedImageItemView
import com.example.mydictionary.interactors.RepositoryInteractor
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import javax.inject.Inject

class AddImagePresenter @Inject constructor(
    private val repositoryInteractor: RepositoryInteractor,
    private val router: Router,
    private val screens: IScreens,
    private val mainScheduler: Scheduler,
    val rvImagesPresenter: IRVPresenter<Image, ISelectedImageItemView>,
) : MvpPresenter<AddImageView>() {
    private var card: Card? = null
    private val listOfImages = mutableListOf<Image>()

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
        listOfImages
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
                    if (listOfImages.size > 0) {
                        repositoryInteractor.saveImages(uid, listOfImages).observeOn(mainScheduler)
                            .subscribe()
                    }
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