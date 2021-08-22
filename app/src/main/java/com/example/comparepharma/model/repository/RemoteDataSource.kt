package com.example.comparepharma.model.repository

import com.example.comparepharma.model.Constants
import com.example.comparepharma.model.dto.SearchAprilDTO
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource{
    private val pharmaAPI = Retrofit.Builder()
        .baseUrl(Constants.APTEKA_APRIL_BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        ).build().create(PharmaAPI::class.java)

    fun getPharmaDetails(id: Int, callback: Callback<List<SearchAprilDTO>>){
        pharmaAPI.getPharma(id).enqueue(callback)
    }
}