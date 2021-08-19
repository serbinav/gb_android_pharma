package com.example.comparepharma.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.comparepharma.R
import com.example.comparepharma.databinding.MainDetailsFragmentBinding
import com.example.comparepharma.model.AppState
import com.example.comparepharma.model.data.MedicineCost
import com.example.comparepharma.viewmodel.DetailsViewModel

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

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        medCostBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: MedicineCost()
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getPharmaFromRemoteSource("https://web-api.apteka-april.ru/catalog/products?ID=${medCostBundle.medicament.id}&cityID=168660")
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.main.show()
                binding.loadingLayout.hide()
                setPharma(appState.pharmaData.first())
            }
            is AppState.Loading -> {
                binding.main.hide()
                binding.loadingLayout.show()
            }
            is AppState.Error -> {
                binding.main.show()
                binding.loadingLayout.hide()
                binding.main.showSnackBar(
                    getString(R.string.error_title),
                    getString(R.string.reload)
                ) {
                    viewModel.getPharmaFromRemoteSource("https://web-api.apteka-april.ru/catalog/products?ID=${medCostBundle.medicament.id}&cityID=168660")
                }
            }
        }
    }

    private fun setPharma(pharma: MedicineCost) {
        with(binding) {
            name.text = pharma.medicament.tradeName
            releaseForm.text = pharma.medicament.releaseForm
            dosage.text = pharma.medicament.dosage
            vendor.text = pharma.medicament.vendor
            price.text = pharma.price
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