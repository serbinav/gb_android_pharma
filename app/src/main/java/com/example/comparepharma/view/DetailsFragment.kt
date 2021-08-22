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
import com.example.comparepharma.model.Constants
import com.example.comparepharma.model.data.MedicineCost
import com.example.comparepharma.viewmodel.DetailsViewModel
import com.squareup.picasso.Picasso

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
        viewModel.detailsLiveData.observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getPharmaFromRemoteSource(medCostBundle.medicament.id.toInt())
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
                    viewModel.getPharmaFromRemoteSource(medCostBundle.medicament.id.toInt())
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

            Picasso
                .get()
                .load(Constants.TEST_IMG)
                .into(image)
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