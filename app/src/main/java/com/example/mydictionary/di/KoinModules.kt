package com.example.mydictionary.di

import androidx.room.Room
import com.example.domain.IRepository
import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.Image
import com.example.mydictionary.domain.WordTranslation
import com.example.mydictionary.domain.interfaces.IImageLoader
import com.example.mydictionary.domain.interfaces.IScreens
import com.example.mydictionary.domain.interfaces.Mapper
import com.example.mydictionary.extensions.GlideImageLoader
import com.example.mydictionary.interactors.RepositoryInteractor
import com.example.mydictionary.mappers.CardMapper
import com.example.mydictionary.mappers.ImageMapper
import com.example.mydictionary.mappers.WordTranslationMapper
import com.example.mydictionary.models.Repository
import com.example.mydictionary.models.retrofit.SkyEngApi
import com.example.mydictionary.models.room.AppDataBase
import com.example.mydictionary.models.room.RoomCard
import com.example.mydictionary.models.room.RoomImage
import com.example.mydictionary.models.room.RoomWordTranslation
import com.example.mydictionary.viewmodels.*
import com.example.mydictionary.views.Screens
import com.github.terrakok.cicerone.Cicerone
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KoinModules {
    const val IMAGE_MAPPER = "imageMapper"
    const val WORD_TRANSLATION_MAPPER = "wordTranslationMapper"
    const val CARD_MAPPER = "cardMapper"
    const val SKY_ENG_URL = "skyEngUrl"
    const val DATABASE_NAME = "dataBaseName"

    val cicerone = module {
        val cicerone = Cicerone.create()
        single { cicerone.router }
        single { cicerone.getNavigatorHolder() }
        single<IScreens> { Screens() }
    }

    val viewModel = module {
        viewModel { MainViewModel(get(), get()) }
        viewModel {
            AddTranslationViewModel(
                screens = get(),
                router = get(),
                repositoryInteractor = get()
            )
        }
        viewModel {
            AddCardViewModel(
                screens = get(),
                router = get()
            )
        }
    }

    val appContext = module {
        single(named("appContext")) { androidContext() }
    }

    val presenter = module {
        factory {
            CardsPresenter(
                repositoryInteractor = get(),
                wordsPresenter = get<CardsPresenter.WordsListPresenter>(),
                router = get(),
                screens = get()
            )
        }
        factory {
            CardPresenter(
                rvPresenter = get<CardPresenter.RVCardImagesPresenter>(),
                repositoryInteractor = get(),
            )
        }
        factory { AddImagePresenter(
            repositoryInteractor = get(),
            router = get(),
            screens = get(),
            rvImagesPresenter = get<AddImagePresenter.RVImagesPresenter>()
        ) }
        factory { CardsPresenter.WordsListPresenter() }
        factory { AddImagePresenter.RVImagesPresenter() }
        factory { CardPresenter.RVCardImagesPresenter() }

    }

    val retrofit = module {
        single(named(SKY_ENG_URL)) { "https://dictionary.skyeng.ru/api/public/v1/" }
        single { GsonBuilder().create() }
        single {
            Retrofit
                .Builder()
                .baseUrl(get<String>(named(SKY_ENG_URL)))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(get()))
                .build()
        }
        single<SkyEngApi> { get<Retrofit>().create(SkyEngApi::class.java) }
    }

    val db = module {
        single(named(DATABASE_NAME)) { "mydictionary.db" }
        single {
            Room
                .databaseBuilder(
                    get(),
                    AppDataBase::class.java,
                    get<String>(named(DATABASE_NAME))
                )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    val mapper = module {
        single<Mapper<RoomCard, Card>>(named(CARD_MAPPER)) { CardMapper() }
        single<Mapper<RoomWordTranslation, WordTranslation>>(named(WORD_TRANSLATION_MAPPER)) { WordTranslationMapper() }
        single<Mapper<RoomImage, Image>>(named(IMAGE_MAPPER)) { ImageMapper() }
    }

    val repository = module {
        single<IRepository> { Repository(get(), get()) }
        single {
            RepositoryInteractor(
                repo = get(),
                cardMapper = get(named(CARD_MAPPER)),
                wordTranslationsMapper = get(named(WORD_TRANSLATION_MAPPER)),
                imageMapper = get(named(IMAGE_MAPPER))
            )
        }
    }

    val imageLoader = module {
        factory<IImageLoader> { GlideImageLoader(get()) }
    }
}