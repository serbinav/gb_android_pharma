package com.example.comparepharma.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val medicineId: String,
    val tradeName: String,
    val price: String
)