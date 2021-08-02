package com.example.comparepharma.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.comparepharma.model.AppState
import com.example.comparepharma.model.repository.RepositorySingleImpl
import com.example.comparepharma.repository.Repository

class MainViewModel(private val repository: Repository = RepositorySingleImpl) :
    ViewModel() {

    private val liveDataToObserver: MutableLiveData<AppState> = MutableLiveData()

    private var counter: Int = 0

    fun getDate(): LiveData<AppState> {
        return liveDataToObserver
    }

    fun getPharmaFromLocal(){
        liveDataToObserver.value = AppState.Loading
        Thread{
            Thread.sleep(1000)
            counter++
            liveDataToObserver.postValue(AppState.Success(repository.getPharmasFromLocal()))
        }.start()
    }

    fun getPharmaFromRemote(){
        liveDataToObserver.value = AppState.Loading
        Thread{
            Thread.sleep(2000)
            counter++
            liveDataToObserver.postValue(AppState.Success(repository.getPharmasFromServer()))
        }.start()
    }
}