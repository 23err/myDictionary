package com.example.mydictionary.domain.interfaces

import com.example.mydictionary.domain.Card
import com.github.terrakok.cicerone.Screen

interface IScreens {
    fun wordList(): Screen
    fun addWord(): Screen
    fun addTranslation(card: Card): Screen
    fun addImage(card: Card): Screen
    fun card(card: Card): Screen
}