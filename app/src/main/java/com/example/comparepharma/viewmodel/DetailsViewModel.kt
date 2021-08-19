package com.example.comparepharma.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.comparepharma.model.AppState
import com.example.comparepharma.model.data.convertDtoToModel
import com.example.comparepharma.model.dto.SearchAprilDTO
import com.example.comparepharma.model.repository.DetailsRepository
import com.example.comparepharma.model.repository.DetailsRepositoryImpl
import com.example.comparepharma.model.repository.RemoteDataSource
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_ERROR = "Неполные данные"

class DetailsViewModel(
    private val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsRepository: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource())
) : ViewModel() {
    fun getLiveData() = detailsLiveData

    fun getPharmaFromRemoteSource(requestLink: String) {
        detailsLiveData.value = AppState.Loading
        detailsRepository.getPharmaDetailsFromServer(requestLink, callback)
    }

    private val callback = object : Callback {

        @Throws(IOException::class)
        override fun onResponse(call: Call, response: Response) {
            val serverResponse = response.body()?.string()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call?, e: IOException?) {
            detailsLiveData.postValue(AppState.Error(Throwable(e?.message ?: REQUEST_ERROR)))
        }
    }

    fun checkResponse(serverResponse: String): AppState {
        val searchAprilDTO: Array<SearchAprilDTO> =
            Gson().fromJson(serverResponse, Array<SearchAprilDTO>::class.java)
        searchAprilDTO.first().apply {
            val description = description.first()
            val properties = properties.first()
            return if (name == null || description == null || properties == null || price == null) {
                AppState.Error(Throwable(CORRUPTED_ERROR))
            } else {
                AppState.Success(convertDtoToModel(this))
            }
        }
    }
}