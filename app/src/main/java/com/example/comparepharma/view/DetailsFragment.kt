package com.example.comparepharma.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.comparepharma.databinding.MainDetailsFragmentBinding
import com.example.comparepharma.model.data.MedicineCost
import com.example.comparepharma.model.dto.*

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
        val loader = PharmaLoader(onLoadListener, medCostBundle.medicament.id)
        loader.loadMedicineCost()
    }

    private val onLoadListener = object : PharmaLoader.PharmaLoaderListener{
        override fun onLoaded(searchAprilDTO: SearchAprilDTO) {
            displayPharma(searchAprilDTO)
        }

        override fun onFailed(throwable: Throwable) {
            // Обработка ошибок
        }
    }

    private fun displayPharma(pharmaDTO: SearchAprilDTO) {
        with(binding) {
            main.show()
            loadingLayout.hide()
            pharmaDTO.let { medicine ->
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