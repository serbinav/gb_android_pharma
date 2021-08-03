package com.example.comparepharma.repository

import com.example.comparepharma.model.data.Cost

interface Repository {
    fun getPharmasFromServer(): List<Cost>
    fun getPharmasFromLocal(): List<Cost>
}