package com.example.comparepharma.model.repository

import okhttp3.Callback

interface DetailsRepository {
    fun getPharmaDetailsFromServer(requestLink: String, callback: Callback)
}