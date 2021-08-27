package com.example.comparepharma.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.comparepharma.app.App
import com.example.comparepharma.model.AppState
import com.example.comparepharma.model.repository.LocalRepository
import com.example.comparepharma.model.repository.LocalRepositoryImpl

class FavoritesViewModel(
    val favoritesLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val favoritesRepository: LocalRepository = LocalRepositoryImpl(App.getFavoritesDao())
) : ViewModel() {

    fun getAllFavorites() {
        favoritesLiveData.value = AppState.Loading
        favoritesLiveData.value = AppState.Success(favoritesRepository.getAllFavorites())
    }
}