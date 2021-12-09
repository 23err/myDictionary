package com.example.mydictionary.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class BaseViewModel<T>() : ViewModel() {

    protected val liveDataForViewToObserve: MutableLiveData<AppState<T>> = MutableLiveData()
    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
    + SupervisorJob()
    + CoroutineExceptionHandler{ _, throwable->
            handleError(throwable)
        }
    )

    abstract fun handleError(throwable: Throwable)

    fun getLiveData(): LiveData<AppState<T>> = liveDataForViewToObserve
}


