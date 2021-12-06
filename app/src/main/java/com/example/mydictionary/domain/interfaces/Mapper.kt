package com.example.mydictionary.domain.interfaces

interface Mapper<T,R> {
    fun map(src: T): R
    fun reverseMap(src: R): T
}