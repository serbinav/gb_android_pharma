package com.example.comparepharma.model

import com.example.comparepharma.model.data.Cost

sealed class AppState{
    data class Success(val pharmaData: List<Cost>): AppState()
    class Error(val error: Throwable):AppState()
    object Loading: AppState()
}