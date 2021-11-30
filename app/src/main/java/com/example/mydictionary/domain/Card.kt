package com.example.mydictionary.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Card(
    var uid: Long? = null,
    var value: String,
    var imageUrl: String? = null,
    val translationWords: MutableList<String> = mutableListOf()
): Parcelable
