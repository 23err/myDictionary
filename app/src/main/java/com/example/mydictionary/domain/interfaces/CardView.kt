package com.example.mydictionary.domain.interfaces

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface CardView : MvpView {
    fun setTitle(text:String)
    fun notifyDataSetChanged()
}