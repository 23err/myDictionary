package com.example.mydictionary

import android.app.Application
import com.example.mydictionary.di.AppComponent
import com.example.mydictionary.di.AppModule
import com.example.mydictionary.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent
    private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
    companion object{
        lateinit var instance: App
            private set
    }
}