package com.example.comparepharma.model

import com.example.comparepharma.model.data.MedicineCost

sealed class AppState{
    data class Success(val pharmaData: List<MedicineCost>): AppState()
    class Error(val error: Throwable):AppState()
    object Loading: AppState()
}

sealed class ContactState{
    class Success(val data: List<String>) : ContactState()
    object Loading : ContactState()
}