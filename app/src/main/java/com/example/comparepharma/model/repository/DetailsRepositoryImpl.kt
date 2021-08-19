package com.example.comparepharma.model.repository

import com.example.comparepharma.model.dto.SearchAprilDTO
import retrofit2.Callback

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DetailsRepository {
    override fun getPharmaDetailsFromServer(id: Int, callback: Callback<List<SearchAprilDTO>>) {
        remoteDataSource.getPharmaDetails(id, callback)
    }
}