package com.example.mydictionary.domain.interfaces


import android.widget.ImageView

interface IImageLoader{
    fun load(url: String, view: ImageView)
}