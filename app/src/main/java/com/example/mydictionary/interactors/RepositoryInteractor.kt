package com.example.mydictionary.interactors

import com.example.domain.IRepository
import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.Image
import com.example.mydictionary.domain.WordTranslation
import com.example.mydictionary.domain.interfaces.Mapper
import com.example.mydictionary.mappers.CardMapper
import com.example.mydictionary.mappers.ImageMapper
import com.example.mydictionary.mappers.WordTranslationMapper
import com.example.mydictionary.models.room.RoomCard
import com.example.mydictionary.models.room.RoomImage
import com.example.mydictionary.models.room.RoomWordTranslation

class RepositoryInteractor (
    private val repo: IRepository,
    private val cardMapper: Mapper<RoomCard, Card> = CardMapper(),
    private val wordTranslationsMapper: Mapper<RoomWordTranslation, WordTranslation> = WordTranslationMapper(),
    private val imageMapper: Mapper<RoomImage, Image> = ImageMapper(),
) {
    suspend fun getCards(): List<Card> =
        repo.getCards().map { roomCard ->
           cardMapper.map(roomCard)
        }

    suspend fun saveCard(card: Card) = repo.saveCard(cardMapper.reverseMap(card))

    suspend fun getWordTranslations(cardUid: Long) = repo.getWordTranslations(cardUid).map {
        wordTranslationsMapper.map(it)
    }

    suspend fun saveTranslationWords(cardUid: Long, wordTranslations: MutableList<WordTranslation>) =
        repo.saveWordTranslations(wordTranslations.map {
            wordTranslationsMapper.reverseMap(it).apply { this.cardUid = cardUid }
        })

    suspend fun saveImages(cardUid: Long, images: List<Image>) = repo.saveImages(images.map {
        imageMapper.reverseMap(it).apply { this.cardUid = cardUid }
    })

    suspend fun getImages(cardUid: Long) = repo.getImages(cardUid).map {
        imageMapper.map(it)
    }

    suspend fun getTranslationsWithImage(word: String): List<WordTranslation> {
        val wordTranslations = repo.getTranslationsWithImage(word)
        if (wordTranslations.size == 0) return listOf<WordTranslation>()

        return wordTranslations[0].meanings
            .filter {
                it.translation != null && !it.translation!!.text.isNullOrEmpty()
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

    private fun addPreffixHttpsOrNull(url: String?): String? =
        if (url.isNullOrEmpty()) null
        else "https:$url"
}