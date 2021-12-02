package com.example.mydictionary.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydictionary.databinding.RvCardImageItemBinding
import com.example.mydictionary.domain.Image
import com.example.mydictionary.domain.interfaces.IImageItemView
import com.example.mydictionary.domain.interfaces.IImageLoader
import com.example.mydictionary.domain.interfaces.IRVPresenter

class CardImagesAdapter(
    private val imageLoader: IImageLoader,
    private val presenter: IRVPresenter<Image, IImageItemView>,
) : RecyclerView.Adapter<CardImagesAdapter.ImageItemView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ImageItemView(
        RvCardImageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ImageItemView, position: Int) {
        presenter.onClickListener?.invoke(position)
    }

    override fun getItemCount() = presenter.getItemCount()

    inner class ImageItemView(private val binding: RvCardImageItemBinding) :
        RecyclerView.ViewHolder(binding.root),
        IImageItemView {
        override fun setImage(url: String) {
            imageLoader.load(url, binding.image)
        }
    }
}