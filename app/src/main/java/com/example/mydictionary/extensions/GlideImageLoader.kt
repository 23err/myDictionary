package com.example.mydictionary.extensions

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.mydictionary.domain.interfaces.IImageLoader

class GlideImageLoader(private val context: Context) : IImageLoader {
    override fun load(url: String, view: ImageView) {
        Glide.with(context).load(url).into(view)
    }
}