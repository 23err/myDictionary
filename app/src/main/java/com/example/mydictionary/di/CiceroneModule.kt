package com.example.mydictionary.di

import androidx.room.Room
import com.example.mydictionary.App
import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.WordTranslation
import com.example.mydictionary.domain.interfaces.IRepository
import com.example.mydictionary.domain.interfaces.IScreens
import com.example.mydictionary.domain.interfaces.Mapper
import com.example.mydictionary.mappers.CardMapper
import com.example.mydictionary.mappers.WordTranslationMapper
import com.example.mydictionary.model.Repository
import com.example.mydictionary.model.room.AppDataBase
import com.example.mydictionary.model.room.RoomCard
import com.example.mydictionary.model.room.RoomWordTranslation
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
    fun repository(db: AppDataBase): IRepository = Repository(db)
}

@Module
class SchedulerModule {
    @Provides
    fun scheduler(): Scheduler = AndroidSchedulers.mainThread()
}

@Module
class PresenterModule {
    @Provides
    fun wordsPresenter(): IRVPresenter = CardsPresenter.WordsListPresenter()
}

@Module
class AppModule(private val app: App) {
    @Provides
    fun app(): App = app
}

@Module
class DatabaseModule {
    @Named("dbName")
    @Singleton
    @Provides
    fun dbName(): String = "mydictionary.db"

    @Singleton
    @Provides
    fun db(app: App, @Named("dbName") dbName: String): AppDataBase =
        Room.databaseBuilder(app, AppDataBase::class.java, dbName)
            .fallbackToDestructiveMigration().build()
}

@Module
class MapperModule {
    @Singleton
    @Provides
    fun cardMapper(): Mapper<RoomCard, Card> = CardMapper()

    @Singleton
    @Provides
    fun wordTranslationMapper(): Mapper<RoomWordTranslation, WordTranslation> = WordTranslationMapper()
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
        MapperModule::class,
    ]
)

interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(cardsFragment: CardsFragment)
    fun inject(addCardFragment: AddCardFragment)
    fun inject(addTranslationFragment: AddTranslationFragment)
    fun inject(addImageFragment: AddImageFragment)
}