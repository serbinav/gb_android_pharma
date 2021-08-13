package com.example.comparepharma.view

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.comparepharma.model.dto.SearchAprilDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class PharmaLoader(
    private val listener: PharmaLoaderListener,
    private val id: String,
    ) {

    interface PharmaLoaderListener {
        fun onLoaded(searchAprilDTO: SearchAprilDTO)
        fun onFailed(throwable: Throwable)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadMedicineCost() {
        try {
            val uri =
                URL("https://web-api.apteka-april.ru/catalog/products?ID=${id}&cityID=168660")
            val handler = Handler(Looper.myLooper()!!)
            Thread (
                Runnable {
                    lateinit var urlConnection: HttpsURLConnection
                    try {
                        urlConnection = uri.openConnection() as HttpsURLConnection
                        urlConnection.requestMethod = "GET"
                        urlConnection.readTimeout = 10000
                        val bufferedReader =
                            BufferedReader(InputStreamReader(urlConnection.inputStream))

                        val aptekaAprilDTO: Array<SearchAprilDTO> =
                            Gson().fromJson(getLines(bufferedReader), Array<SearchAprilDTO>::class.java)
                        handler.post { listener.onLoaded(aptekaAprilDTO[0]) }

                    } catch (e: Exception) {
                        Log.e("PHARMA", "FAIL CONNECTION", e)
                        e.printStackTrace()
                        listener.onFailed(e)
                    } finally {
                        urlConnection.disconnect()
                    }
                }).start()
        } catch (ex: MalformedURLException) {
            Log.e("PHARMA", "FAIL URI", ex)
            ex.printStackTrace()
            listener.onFailed(ex)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }
}