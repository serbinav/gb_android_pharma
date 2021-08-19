package com.example.comparepharma.model.repository

import okhttp3.Callback

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DetailsRepository {
    override fun getPharmaDetailsFromServer(requestLink: String, callback: Callback) {
        remoteDataSource.getPharmaDetails(requestLink, callback)
    }
}