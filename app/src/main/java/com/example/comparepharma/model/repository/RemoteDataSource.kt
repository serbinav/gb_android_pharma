package com.example.comparepharma.model.repository

import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

class RemoteDataSource{
    fun getPharmaDetails(requestLink: String, callback: Callback){
        val builder = Request.Builder().apply {
            url(requestLink)
        }
        OkHttpClient().newCall(builder.build()).enqueue(callback)
    }
}