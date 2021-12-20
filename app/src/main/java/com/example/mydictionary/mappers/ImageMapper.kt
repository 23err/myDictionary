package com.example.mydictionary.mappers

import com.example.mydictionary.domain.Image
import com.example.mydictionary.domain.interfaces.Mapper
import com.example.mydictionary.models.room.RoomImage

class ImageMapper : Mapper<RoomImage, Image> {
    override fun map(src: RoomImage) = Image(src.uid, src.url, src.previewUrl, src.cardUid)

    override fun reverseMap(src: Image)=RoomImage(src.uid, src.url,src.previewUrl, src.cardUid ?: 0)
}