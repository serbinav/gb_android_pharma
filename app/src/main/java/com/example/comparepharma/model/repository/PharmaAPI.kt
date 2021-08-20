package com.example.comparepharma.model.repository

import com.example.comparepharma.model.dto.SearchAprilDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PharmaAPI {
    @GET("catalog/products")
    fun getPharma(
        @Query("ID") id: Int,
        @Query("cityID") cityID: Int = 168660
    ): Call<List<SearchAprilDTO>>
}