package com.example.comparepharma.room

import android.database.Cursor
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

    @Query("DELETE FROM FavoritesEntity WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT medicineId, tradeName, price FROM FavoritesEntity")
    fun getFavoritesCursor(): Cursor

    @Query("SELECT medicineId, tradeName, price FROM FavoritesEntity WHERE id = :id")
    fun getFavoritesCursor(id: Long): Cursor
}