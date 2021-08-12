package com.example.comparepharma.view

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.comparepharma.databinding.MainDetailsFragmentBinding
import com.example.comparepharma.model.data.MedicineCost
import com.example.comparepharma.model.dto.*
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class DetailsFragment : Fragment() {

    private var _binding: MainDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var medCostBundle: MedicineCost

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        medCostBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: MedicineCost()
        binding.main.hide()
        binding.loadingLayout.show()
        loadMedicineCost()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadMedicineCost() {
        try {
            val uri =
                URL("https://web-api.apteka-april.ru/catalog/products?ID=${medCostBundle.medicament.id}&cityID=168660")
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
                        handler.post { displayPharma(aptekaAprilDTO[0]) }

                    } catch (e: Exception) {
                        Log.e("PHARMA", "FAIL CONNECTION", e)
                        e.printStackTrace()
                    } finally {
                        urlConnection.disconnect()
                    }
            }).start()
        } catch (ex: MalformedURLException) {
            Log.e("PHARMA", "FAIL URI", ex)
            ex.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun displayPharma(pharmaDTO: SearchAprilDTO) {
        with(binding) {
            main.show()
            loadingLayout.hide()
            pharmaDTO?.let { medicine ->
                name.text = medicine.name
                releaseForm.text = medicine.description.filter { it?.typeID == 19 }[0]?.description
                dosage.text = medicine.properties.filter { it?.typeID == 20 }[0]?.name
                vendor.text = medicine.properties.filter { it?.typeID == 13 }[0]?.name
                price.text = medicine.price?.withoutCard.toString()
            }
        }
    }

    companion object {
        const val BUNDLE_EXTRA = "cost"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}