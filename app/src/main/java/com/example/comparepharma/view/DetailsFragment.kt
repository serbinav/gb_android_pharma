package com.example.comparepharma.view

import android.app.AlertDialog
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
import java.lang.RuntimeException

const val VENDOR: Int = 13
const val RELEASE_FORM: Int = 19
const val DOSAGE: Int = 20

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
        try {
            loader.loadMedicineCost()
        }
        catch(e: RuntimeException){
            val dialogBuilder = AlertDialog.Builder(requireContext())
            dialogBuilder
                .setMessage("Не удалось отобразить данные")
                .setCancelable(false)
                .setPositiveButton("OK") { dialog, id ->
                    fragmentManager?.popBackStack()
                }
            val alert = dialogBuilder.create()
            alert.setTitle("Ошибка")
            alert.show()
        }
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
                releaseForm.text = medicine.description.first { it?.typeID == RELEASE_FORM }?.description
                dosage.text = medicine.properties.first { it?.typeID == DOSAGE }?.name
                vendor.text = medicine.properties.first { it?.typeID == VENDOR }?.name
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