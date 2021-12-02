package com.example.mydictionary.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WordTranslation(
    var uid: Long? = null,
    var value: String,
    var cardUid: Long? = null,
    var id: Int? = null,
    var image: Image? = null,
):Parcelable
