package com.example.comparepharma.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.comparepharma.model.AppState
import com.example.comparepharma.model.repository.RepositorySingleImpl
import com.example.comparepharma.model.repository.Repository

private const val WAIT_LONG: Long = 3000

class MainViewModel(private val repository: Repository = RepositorySingleImpl) :
    ViewModel() {

    private val liveDataToObserver: MutableLiveData<AppState> = MutableLiveData()

    fun getDate(): LiveData<AppState> {
        return liveDataToObserver
    }

    fun getPharmaFromLocalAptekaRu() = getPharmaFromLocalSource(isAptekaRu = true)

    fun getPharmaFromLocalAptekaApril() = getPharmaFromLocalSource(isAptekaRu = false)

    private fun getPharmaFromLocalSource(isAptekaRu: Boolean) {
        liveDataToObserver.value = AppState.Loading
        Thread {
            Thread.sleep(WAIT_LONG)
            liveDataToObserver.postValue(
                AppState.Success(
                    if (isAptekaRu) {
                        repository.getPharmaFromLocalAptekaRu()
                    } else {
                        repository.getPharmaFromLocalAptekaApril()
                    }
                )
            )
        }.start()
    }
}