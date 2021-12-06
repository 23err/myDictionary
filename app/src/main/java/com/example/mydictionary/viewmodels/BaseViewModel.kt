package com.example.mydictionary.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel<T>(
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val liveDataForViewToObserve: MutableLiveData<AppState<T>> = MutableLiveData(),
) : ViewModel() {

    fun getLiveData(): LiveData<AppState<T>> = liveDataForViewToObserve

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}


sealed class AppState<T> {
    data class Success<T>(val data: T) : AppState<T>()
    data class Error<T>(val error: Throwable) : AppState<T>()
    data class Loading<T>(val status: String = "loading") : AppState<T>()
}
