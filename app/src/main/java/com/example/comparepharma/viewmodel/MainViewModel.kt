package com.example.comparepharma.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.comparepharma.model.AppState

class MainViewModel() :
    ViewModel() {

    private val liveDataToObserver: MutableLiveData<AppState> = MutableLiveData()

    private var counter: Int = 0

    fun getDate(): LiveData<AppState> {
        return liveDataToObserver
    }

    fun requestData(data: String){
        liveDataToObserver.value = AppState.Loading
        Thread{
            Thread.sleep(2000)
            counter++
            liveDataToObserver.postValue(AppState.Success(data + counter))
        }.start()
    }
}