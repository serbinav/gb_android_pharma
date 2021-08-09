package com.example.comparepharma.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.comparepharma.databinding.MainDetailsFragmentBinding
import com.example.comparepharma.model.data.MedicineCost

class DetailsFragment : Fragment() {

    private var _binding: MainDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<MedicineCost>(BUNDLE_EXTRA)?.let { pharma -> bindData(pharma) }
    }

    private fun bindData(pharmaData: MedicineCost) {
        with(binding) {
            pharmaData.medicament.also { medicine ->
                name.text = medicine.tradeName
                releaseForm.text = medicine.releaseForm
                dosage.text = medicine.dosage
                vendor.text = medicine.vendor
            }
            price.text = pharmaData.price.toString()
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