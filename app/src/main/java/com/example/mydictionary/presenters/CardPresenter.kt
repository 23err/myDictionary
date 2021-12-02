package com.example.mydictionary.presenters

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.Image
import com.example.mydictionary.domain.interfaces.CardView
import com.example.mydictionary.domain.interfaces.IImageItemView
import com.example.mydictionary.domain.interfaces.IRVPresenter
import moxy.MvpPresenter
import javax.inject.Inject

class CardPresenter @Inject constructor(
    val rvPresenter:IRVPresenter<Image, IImageItemView>,
) : MvpPresenter<CardView>() {
    fun init(card: Card) {
        viewState.setTitle(card.value)
        card.wordTranslations?.let{
            val images = it.filter { it.image != null }.map { it.image!! }
            rvPresenter.list.addAll(images)
            viewState.notifyDataSetChanged()
        }
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