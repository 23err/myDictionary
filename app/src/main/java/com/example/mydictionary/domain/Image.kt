package com.example.mydictionary.domain

data class Image(
    var uid: Long? = null,
    var url: String,
    var previewUrl: String? = null,
)
