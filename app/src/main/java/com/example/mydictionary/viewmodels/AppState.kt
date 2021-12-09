package com.example.mydictionary.viewmodels

sealed class AppState<T> {
    data class Success<T>(val data: T) : AppState<T>()
    data class Error<T>(val error: Throwable) : AppState<T>()
    data class Loading<T>(val status: String = "loading") : AppState<T>()
}