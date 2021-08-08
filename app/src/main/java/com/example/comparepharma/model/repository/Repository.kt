package com.example.comparepharma.model.repository

import com.example.comparepharma.model.data.MedicineCost

interface Repository {
    fun getPharmaFromServer(): List<MedicineCost>
    fun getPharmaFromLocalAptekaRu(): List<MedicineCost>
    fun getPharmaFromLocalAptekaApril(): List<MedicineCost>
}