package com.example.comparepharma.model.repository

import com.example.comparepharma.model.dto.SearchAprilDTO
import retrofit2.Callback

interface DetailsRepository {
    fun getPharmaDetailsFromServer(id: Int, callback: Callback<List<SearchAprilDTO>>)
}