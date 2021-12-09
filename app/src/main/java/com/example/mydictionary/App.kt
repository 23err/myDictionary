package com.example.mydictionary

import android.app.Application
import com.example.mydictionary.di.KoinModules.appContext
import com.example.mydictionary.di.KoinModules.cicerone
import com.example.mydictionary.di.KoinModules.db
import com.example.mydictionary.di.KoinModules.imageLoader
import com.example.mydictionary.di.KoinModules.mapper
import com.example.mydictionary.di.KoinModules.presenter
import com.example.mydictionary.di.KoinModules.repository
import com.example.mydictionary.di.KoinModules.retrofit
import com.example.mydictionary.di.KoinModules.viewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidContext(this@App)
            modules(
                mapper,
                cicerone,
                viewModel,
                appContext,
                imageLoader,
                presenter,
                retrofit,
                db,
                repository,
            )
        }
    }

    companion object {
        lateinit var instance: App
            private set
    }
}