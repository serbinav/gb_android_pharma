package com.example.comparepharma.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.comparepharma.databinding.MainDetailsFragmentBinding
import com.example.comparepharma.model.data.MedicineCost
import com.example.comparepharma.model.dto.*
import com.example.comparepharma.service.DetailsService
import com.example.comparepharma.service.ID_EXTRA

const val VENDOR: Int = 13
const val RELEASE_FORM: Int = 19
const val DOSAGE: Int = 20

const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
const val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"
const val DETAILS_INTENT_EMPTY_EXTRA = "INTENT IS EMPTY"
const val DETAILS_DATA_EMPTY_EXTRA = "DATA IS EMPTY"
const val DETAILS_RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
const val DETAILS_REQUEST_ERROR_EXTRA = "REQUEST ERROR"
const val DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "REQUEST ERROR MESSAGE"
const val DETAILS_URL_MALFORMED_EXTRA = "URL MALFORMED"
const val DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"
const val DETAILS_NAME_EXTRA = "NAME"
const val DETAILS_RELEASE_FORM_EXTRA = "RELEASE FORM"
const val DETAILS_DOSAGE_EXTRA = "DOSAGE"
const val DETAILS_VENDOR_EXTRA = "VENDOR"
const val DETAILS_PRICE_EXTRA = "PRICE"
private const val EMPTY_INT = 0
private const val EMPTY_STRING = ""
private const val PROCESS_ERROR = "Обработка ошибки"

class DetailsFragment : Fragment() {

    private var _binding: MainDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var medCostBundle: MedicineCost

    private val loadResultReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_INTENT_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_DATA_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_URL_MALFORMED_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_SUCCESS_EXTRA -> renderData(
                    SearchAprilDTO(
                        price = PriceAprilDTO(
                            EMPTY_INT,
                            EMPTY_INT,
                            intent.getIntExtra(DETAILS_PRICE_EXTRA, EMPTY_INT)
                        ),
                        name = intent.getStringExtra(DETAILS_NAME_EXTRA),
                        description = arrayOf(
                            DescriptionAprilDTO(
                                EMPTY_STRING, RELEASE_FORM, intent.getStringExtra(
                                    DETAILS_RELEASE_FORM_EXTRA
                                )
                            )
                        ),
                        properties = arrayOf(
                            PropertiesAprilDTO(
                                EMPTY_INT,
                                intent.getStringExtra(DETAILS_DOSAGE_EXTRA),
                                EMPTY_STRING,
                                DOSAGE
                            ),
                            PropertiesAprilDTO(
                                EMPTY_INT,
                                intent.getStringExtra(DETAILS_VENDOR_EXTRA),
                                EMPTY_STRING,
                                VENDOR
                            )
                        ),
                        ID = EMPTY_INT,
                        isRecipe = false
                    )
                )
                else -> TODO(PROCESS_ERROR)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainDetailsFragmentBinding.inflate(inflater, container, false)
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(loadResultReceiver, IntentFilter(DETAILS_INTENT_FILTER))
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        medCostBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: MedicineCost()
        getPharma()
    }

    private fun getPharma() {
        binding.main.show()
        binding.loadingLayout.hide()
        context?.let {
            it.startService(Intent(it, DetailsService::class.java).apply {
                putExtra(ID_EXTRA, medCostBundle.medicament.id)
            })
        }
    }

    override fun onDestroyView() {
        _binding = null
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultReceiver)
        }
        super.onDestroyView()
    }

    private fun renderData(pharmaDTO: SearchAprilDTO) {
        binding.main.show()
        binding.loadingLayout.hide()

        val namePharma = pharmaDTO.name
        val releaseFormPharma =
            pharmaDTO.description.first { it?.typeID == RELEASE_FORM }?.description
        val dosagePharma = pharmaDTO.properties.first { it?.typeID == DOSAGE }?.name
        val vendorPharma = pharmaDTO.properties.first { it?.typeID == VENDOR }?.name
        val pricePharma = pharmaDTO.price?.withoutCard

        if (namePharma == EMPTY_STRING ||
            releaseFormPharma == EMPTY_STRING ||
            dosagePharma == EMPTY_STRING ||
            vendorPharma == EMPTY_STRING ||
            pricePharma == EMPTY_INT
        ) {
            TODO(PROCESS_ERROR)
        } else {
            with(binding) {
                name.text = namePharma
                releaseForm.text = releaseFormPharma
                dosage.text = dosagePharma
                vendor.text = vendorPharma
                price.text = pricePharma.toString()
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