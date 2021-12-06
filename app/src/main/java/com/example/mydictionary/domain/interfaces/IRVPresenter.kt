package com.example.mydictionary.domain.interfaces

interface IRVPresenter <E,I>{
    val list: MutableList<E>
    var onClickListener: ((Int) -> Unit)?
    fun onBind(itemView: I, position: Int)
    fun getItemCount(): Int
}