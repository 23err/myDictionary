package com.example.mydictionary.mappers

import com.example.mydictionary.domain.WordTranslation
import com.example.mydictionary.domain.interfaces.Mapper
import com.example.mydictionary.model.room.RoomWordTranslation

class WordTranslationMapper : Mapper<RoomWordTranslation, WordTranslation> {
    override fun map(src: RoomWordTranslation) = WordTranslation(src.uid, src.value, src.cardUid)

    override fun reverseMap(src: WordTranslation) = RoomWordTranslation(src.uid, src.value, src.cardUid ?: 0)
}