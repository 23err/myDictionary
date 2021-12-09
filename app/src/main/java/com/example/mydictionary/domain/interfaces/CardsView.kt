package com.example.mydictionary.domain.interfaces

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface CardsView : MvpView {
    fun notifyDataSetChanged()
    fun showError(message: String)
}