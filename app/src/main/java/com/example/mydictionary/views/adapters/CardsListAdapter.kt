package com.example.mydictionary.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydictionary.databinding.RvCardsItemBinding
import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.interfaces.IImageLoader
import com.example.mydictionary.domain.interfaces.IRVPresenter
import com.example.mydictionary.domain.interfaces.IWordView

class WordsListAdapter(
    private val presenter: IRVPresenter<Card, IWordView>,
    private val imageLoader: IImageLoader,
) : RecyclerView.Adapter<WordsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RvCardsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        presenter.onBind(holder, position)
        holder.itemView.setOnClickListener{
            presenter.onClickListener?.invoke(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return presenter.getItemCount()
    }

    inner class ViewHolder(private val binding: RvCardsItemBinding) : RecyclerView.ViewHolder(binding.root),
        IWordView {
        override fun setLabel(text: String) {
            binding.label.text = text
        }

        override fun setImage(url: String) {
            imageLoader.load(url, binding.image)
        }

    }
}

