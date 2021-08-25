package com.example.comparepharma.room

import androidx.room.*

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM FavoritesEntity")
    fun all(): List<FavoritesEntity>

    @Query("SELECT * FROM FavoritesEntity WHERE medicineId LIKE :medicineId")
    fun getDataById(medicineId: String): FavoritesEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: FavoritesEntity)

    @Update
    fun update(entity: FavoritesEntity)

    @Delete
    fun delete(entity: FavoritesEntity)
}