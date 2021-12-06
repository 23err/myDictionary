package com.example.mydictionary.model.retrofit.beans

data class RFMeaning(
    val id: Int,
    val imageUrl: String?,
    val previewUrl: String?,
    val translation: RFTranslation?
)