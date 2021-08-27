package com.example.comparepharma.model.repository

import com.example.comparepharma.model.data.MedicineCost

interface LocalRepository {
    fun getAllFavorites(): List<MedicineCost>
    fun saveFavorites(medicineCost: MedicineCost)
}