package com.example.mydictionary.model.retrofit

import com.example.mydictionary.model.retrofit.beans.RFWordTranslations
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface SkyEngApi {
    @GET("words/search")
    fun getTranslate(@Query("search") word:String): Deferred<RFWordTranslations>
}