package com.example.comparepharma.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(private val liveDataToObserver: MutableLiveData<Any> = MutableLiveData()) :
    ViewModel() {

    fun getDate(): LiveData<Any> {
        getDataFromLocalSource()
        return liveDataToObserver
    }

    private fun getDataFromLocalSource(){
        Thread{
            Thread.sleep(2000)
            liveDataToObserver.postValue(Any())
        }.start()
    }
}