package com.example.mydictionary.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    var uid: Long? = null,
    var url: String,
    var previewUrl: String? = null,
    var cardUid: Long? = null,
): Parcelable
