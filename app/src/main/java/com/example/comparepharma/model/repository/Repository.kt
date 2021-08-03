package com.example.comparepharma.model.repository

import com.example.comparepharma.model.data.Cost

interface Repository {
    fun getPharmaFromServer(): List<Cost>
    fun getPharmaFromLocal(): List<Cost>
}