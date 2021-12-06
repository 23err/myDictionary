package com.example.mydictionary.model.retrofit.beans

data class RFWordTranslationsItem(
    val id: Int,
    val meanings: List<RFMeaning>,
    val text: String?
)