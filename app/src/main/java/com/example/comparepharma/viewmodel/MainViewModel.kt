package com.example.comparepharma.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.comparepharma.model.AppState
import com.example.comparepharma.model.repository.RepositorySingleImpl
import com.example.comparepharma.model.repository.Repository

private const val WAIT_SMALL : Long = 1000
private const val WAIT_LONG : Long = 2000

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
            Thread.sleep(WAIT_SMALL)
            counter++
            liveDataToObserver.postValue(AppState.Success(repository.getPharmaFromLocal()))
        }.start()
    }

    fun getPharmaFromRemote(){
        liveDataToObserver.value = AppState.Loading
        Thread{
            Thread.sleep(WAIT_LONG)
            counter++
            liveDataToObserver.postValue(AppState.Success(repository.getPharmaFromServer()))
        }.start()
    }
}