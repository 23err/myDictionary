package com.example.mydictionary.views

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.interfaces.IScreens
import com.example.mydictionary.views.fragments.AddImageFragment
import com.example.mydictionary.views.fragments.AddTranslationFragment
import com.example.mydictionary.views.fragments.CardFragment
import com.example.mydictionary.views.fragments.CardsFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen
import layout.AddCardFragment

class Screens : IScreens {
    override fun wordList() = FragmentScreen { CardsFragment() }
    override fun addWord() = FragmentScreen { AddCardFragment() }
    override fun addTranslation(card: Card) = FragmentScreen { AddTranslationFragment.instance(card) }
    override fun addImage(card: Card) = FragmentScreen { AddImageFragment.instance(card) }
    override fun card(card: Card) = FragmentScreen{ CardFragment.instance(card) }
}
