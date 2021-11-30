package com.example.mydictionary.di

import android.content.Context
import androidx.room.Room
import com.example.mydictionary.App
import com.example.mydictionary.domain.interfaces.IRepository
import com.example.mydictionary.domain.interfaces.IScreens
import com.example.mydictionary.model.Repository
import com.example.mydictionary.model.room.AppDataBase
import com.example.mydictionary.presenters.CardsPresenter
import com.example.mydictionary.views.MainActivity
import com.example.mydictionary.views.Screens
import com.example.mydictionary.views.adapters.IRVPresenter
import com.example.mydictionary.views.fragments.AddImageFragment
import com.example.mydictionary.views.fragments.AddTranslationFragment
import com.example.mydictionary.views.fragments.CardsFragment
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import layout.AddCardFragment
import javax.inject.Named
import javax.inject.Singleton

@Module
class CiceroneModule {
    private val cicerone = Cicerone.create()

    @Singleton
    @Provides
    fun router(): Router = cicerone.router

    @Singleton
    @Provides
    fun navigatorHolder(): NavigatorHolder = cicerone.getNavigatorHolder()

    @Singleton
    @Provides
    fun screens(): IScreens = Screens()
}

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun repository(): IRepository = Repository()
}

@Module
class SchedulerModule {
    @Provides
    fun scheduler(): Scheduler = AndroidSchedulers.mainThread()
}

@Module
class PresenterModule{
    @Provides
    fun wordsPresenter(): IRVPresenter = CardsPresenter.WordsListPresenter()
}

@Module
class AppModule(private val app:App){
    @Provides
    fun app(): App = app
}

@Module
class DatabaseModule{
    @Named("dbName")
    @Singleton
    @Provides
    fun dbName():String = "mydictionary.db"
    @Singleton
    @Provides
    fun db(context: Context, @Named("dbName") dbName: String): AppDataBase = Room.databaseBuilder(context, AppDataBase::class.java, dbName).build()
}

@Singleton
@Component(
    modules = [
        CiceroneModule::class,
        RepositoryModule::class,
        SchedulerModule::class,
        PresenterModule::class,
        AppModule::class,
        DatabaseModule::class,
    ]
)

interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(cardsFragment: CardsFragment)
    fun inject(addCardFragment: AddCardFragment)
    fun inject(addTranslationFragment: AddTranslationFragment)
    fun inject(addImageFragment: AddImageFragment)
}