package com.example.comparepharma.model.repository

import com.example.comparepharma.model.data.MedicineCost
import com.example.comparepharma.model.data.convertFavoritesEntityToMedicineCost
import com.example.comparepharma.model.data.convertMedicineCostToEntity
import com.example.comparepharma.room.FavoritesDao

class LocalRepositoryImpl(private val localDataSource: FavoritesDao) : LocalRepository {
    override fun getAllFavorites(): List<MedicineCost> {
        return convertFavoritesEntityToMedicineCost(localDataSource.all())
    }

    override fun saveFavorites(medicineCost: MedicineCost) {
        return localDataSource.insert(convertMedicineCostToEntity(medicineCost))
    }
}