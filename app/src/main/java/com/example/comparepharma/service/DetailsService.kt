package com.example.comparepharma.service

import android.app.IntentService
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.comparepharma.model.dto.SearchAprilDTO
import com.example.comparepharma.view.*
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val ID_EXTRA = "ID"
private const val REQUEST_GET = "GET"
private const val REQUEST_TIMEOUT = 10_000

class DetailsService(name: String = "DetailsService") : IntentService(name) {

    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            onEmptyIntent()
        } else {
            val id = intent.getStringExtra(ID_EXTRA)
            if (id == null) {
                onEmptyData()
            } else {
                loadMedicineCost(id)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadMedicineCost(id:String) {
        try {
            val uri =
                URL("https://web-api.apteka-april.ru/catalog/products?ID=${id}&cityID=168660")
            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.apply {
                    requestMethod = REQUEST_GET
                    readTimeout = REQUEST_TIMEOUT
                }

                val aptekaAprilDTO: Array<SearchAprilDTO> =
                    Gson().fromJson(
                        getLines(
                            BufferedReader(InputStreamReader(urlConnection.inputStream))
                        ),
                        Array<SearchAprilDTO>::class.java
                    )
                onResponse(aptekaAprilDTO)
            } catch (e: Exception) {
                onErrorRequest(e.message ?: "Empty Error")
            } finally {
                urlConnection.disconnect()

            }
        } catch (ex: MalformedURLException) {
            onMalformedURL()
        }
    }

    private fun onResponse(aptekaAprilDTO: Array<SearchAprilDTO>) {
        val med = aptekaAprilDTO.first()
        if (med == null) {
            onEmptyResponse()
        } else {
            val releaseForm = med.description.first { it?.typeID == RELEASE_FORM }?.description
            val dosage = med.properties.first { it?.typeID == DOSAGE }?.name
            val vendor = med.properties.first { it?.typeID == VENDOR }?.name
            onSuccessResponse(
                med.name,
                releaseForm,
                dosage,
                vendor,
                med.price?.withoutCard.toString()
            )
        }
    }

    private fun onSuccessResponse(name: String?, releaseForm: String?, dosage: String?, vendor: String?, toString: String) {

    }

    private fun onEmptyResponse() {
        TODO("Not yet implemented")
    }

    private fun onMalformedURL() {
        TODO("Not yet implemented")
    }

    private fun onErrorRequest(s: String) {

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun onEmptyData() {
        putLoadResult(DETAILS_DATA_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyIntent() {
        putLoadResult(DETAILS_INTENT_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)
    }
}