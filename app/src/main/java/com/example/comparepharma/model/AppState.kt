package com.example.comparepharma.model

sealed class AppState{
    data class Success(val pharmaData: String): AppState()
    class Error(val error: Throwable):AppState()
    object Loading: AppState()
}