package com.example.comparepharma.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.comparepharma.model.AppState
import com.example.comparepharma.model.data.convertDtoToModel
import com.example.comparepharma.model.dto.SearchAprilDTO
import com.example.comparepharma.model.repository.DetailsRepository
import com.example.comparepharma.model.repository.DetailsRepositoryImpl
import com.example.comparepharma.model.repository.RemoteDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_ERROR = "Неполные данные"

class DetailsViewModel(
    val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsRepository: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource())
) : ViewModel() {

    fun getPharmaFromRemoteSource(id: Int) {
        detailsLiveData.value = AppState.Loading
        detailsRepository.getPharmaDetailsFromServer(id, callback)
    }

    private val callback = object : Callback<List<SearchAprilDTO>> {

        override fun onResponse(
            call: Call<List<SearchAprilDTO>>,
            response: Response<List<SearchAprilDTO>>
        ) {
            val serverResponse: List<SearchAprilDTO>? = response.body()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<List<SearchAprilDTO>>, t: Throwable) {
            detailsLiveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }
    }

    fun checkResponse(serverResponse: List<SearchAprilDTO>): AppState {
        serverResponse.first().apply {
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