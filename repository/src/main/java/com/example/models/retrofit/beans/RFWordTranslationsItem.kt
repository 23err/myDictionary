package com.example.mydictionary.models.retrofit.beans

data class RFWordTranslationsItem(
    val id: Int,
    val meanings: List<RFMeaning>,
    val text: String?
)