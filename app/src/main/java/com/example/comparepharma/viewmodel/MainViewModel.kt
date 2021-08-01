package com.example.comparepharma.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel() :
    ViewModel() {

    private val liveDataToObserver: MutableLiveData<String> = MutableLiveData()

    private var counter: Int = 0

    fun getDate(): LiveData<String> {
        return liveDataToObserver
    }

    fun requestData(data: String){
        Thread{
            Thread.sleep(2000)
            counter++
            liveDataToObserver.postValue(data + counter)
        }.start()
    }
}