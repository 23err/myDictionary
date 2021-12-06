package com.example.mydictionary.interactors

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.Image
import com.example.mydictionary.domain.WordTranslation
import com.example.mydictionary.domain.interfaces.IRepository
import com.example.mydictionary.domain.interfaces.Mapper
import com.example.mydictionary.model.room.RoomCard
import com.example.mydictionary.model.room.RoomImage
import com.example.mydictionary.model.room.RoomWordTranslation
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RepositoryInteractor @Inject constructor(
    private val repo: IRepository,
    private val cardMapper: Mapper<RoomCard, Card>,
    private val wordTranslationsMapper: Mapper<RoomWordTranslation, WordTranslation>,
    private val imageMapper: Mapper<RoomImage, Image>,
) {
    fun getCards(): Single<List<Card>> =
        repo.getCards().observeOn(Schedulers.computation()).map { list ->
            list.map {
                cardMapper.map(it)
            }
        }

    fun saveCard(card: Card) = repo.saveCard(cardMapper.reverseMap(card))

    fun getWordTranslations(cardUid: Long) = repo.getWordTranslations(cardUid).map {
        it.map{wordTranslationsMapper.map(it)}
    }

    fun saveTranslationWords(cardUid: Long, wordTranslations: MutableList<WordTranslation>) =
        repo.saveWordTranslations(wordTranslations.map { wordTranslationsMapper.reverseMap(it).apply { this.cardUid = cardUid } })

    fun saveImages(cardUid: Long, images: List<Image>) = repo.saveImages(images.map {
        imageMapper.reverseMap(it).apply { this.cardUid = cardUid }
    })

    fun getImages(cardUid: Long) = repo.getImages(cardUid).map { it.map { imageMapper.map(it) } }

    fun getTranslationsWithImage(word: String): Single<List<WordTranslation>> {
        return repo.getTranslationsWithImage(word).map {
            if (it.size == 0 || it[0].meanings.size == 0) {
                return@map listOf<WordTranslation>()
            }
            return@map it[0].meanings
                .filter {
                    it.translation != null && !it.translation.text.isNullOrEmpty()
                }
                .map {
                    var image: Image? = null
                    val imageUrl = addPreffixHttpsOrNull(it.imageUrl)
                    val previewImageUrl = addPreffixHttpsOrNull(it.previewUrl)
                    imageUrl?.let { url ->
                        image = Image(url = url, previewUrl = previewImageUrl)
                    }
                    WordTranslation(
                        value = it.translation?.text ?: "",
                        id = it.id,
                        image = image
                    )
                }
        }
    }

    private fun addPreffixHttpsOrNull(url: String?): String? =
        if (url.isNullOrEmpty()) null
        else "https:$url"
}