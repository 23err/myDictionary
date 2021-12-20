package com.example.mydictionary.mappers

import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.interfaces.Mapper
import com.example.mydictionary.models.room.RoomCard

class CardMapper : Mapper<RoomCard, Card> {
    override fun map(src: RoomCard) = Card(src.uid, src.value, src.imageUrl)

    override fun reverseMap(src: Card) = RoomCard(src.uid, src.value, src.imageUrl)
}