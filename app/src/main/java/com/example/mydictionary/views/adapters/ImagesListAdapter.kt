package com.example.mydictionary.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydictionary.databinding.RvImagesItemBinding
import com.example.mydictionary.domain.Image
import com.example.mydictionary.domain.interfaces.IImageItemView
import com.example.mydictionary.domain.interfaces.IImageLoader
import com.example.mydictionary.domain.interfaces.IRVPresenter

class ImagesListAdapter(
    private val presenter: IRVPresenter<Image, IImageItemView>,
    private val imageLoader:IImageLoader,

) : RecyclerView.Adapter<ImagesListAdapter.ImageItemView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItemView {
        return ImageItemView(
            RvImagesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageItemView, position: Int) {
        presenter.onBind(holder, position)
        holder.itemView.setOnClickListener{
            holder.setSelected()
            presenter.onClickListener?.invoke(position)
        }
    }

    override fun getItemCount() = presenter.getItemCount()

    inner class ImageItemView(private val binding: RvImagesItemBinding) :
        RecyclerView.ViewHolder(binding.root),
        IImageItemView {
        override fun setImage(url: String) {
          imageLoader.load(url, binding.image)
        }

        override fun setSelected() {
            with(binding.selectButton) {
                visibility = if (visibility == View.INVISIBLE) View.VISIBLE else View.INVISIBLE
            }
        }
    }
}



