package com.example.comparepharma.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.lifecycle.Observer
import com.example.comparepharma.R
import com.example.comparepharma.databinding.MainFragmentBinding
import com.example.comparepharma.model.AppState
import com.example.comparepharma.model.data.MedicineCost
import com.example.comparepharma.utils.*
import com.example.comparepharma.viewmodel.MainViewModel
import java.io.IOException

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var _binding: MainFragmentBinding? = null
    private val binding
        get() = _binding!!
    private val adapter = PharmaAdapter()
    private var isDataSetAptekaRu: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter.setOnItemViewClickListener { cost ->
            openDetailsFragment(cost)
        }

        binding.recyclerView.adapter = adapter
        binding.floatingActionButton.setOnClickListener {
            changeAptekaDataSet()
            saveListOfPharma()
        }

        binding.floatingActionButtonLocation.setOnClickListener {
            checkPermission()
        }

        super.onViewCreated(view, savedInstanceState)

        val observer = Observer<AppState> { renderData(it) }
        viewModel.getDate().observe(viewLifecycleOwner, observer)
        loadListOfPharma()
        showAptekaDataSet()
    }

    private fun checkPermission() {
        requireActivity().let {
            when {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    getLocation()
                }

                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    showRationaleDialog()
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
        }
    }

    private fun getLocation() {
        activity?.let { context ->
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val locationManager =
                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    val provider = locationManager.getProvider(LocationManager.GPS_PROVIDER)
                    provider?.let {
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            Constants.REFRESH_PERIOD,
                            Constants.MINIMAL_DISTANCE,
                            onLocationListener
                        )
                    }
                } else {
                    val location =
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (location == null) {
                        showDialog(
                            getString(R.string.dialog_title_gps_turned_off),
                            getString(R.string.dialog_message_last_location_unknown)
                        )
                    } else {
                        getAddress(context, location)
                        showDialog(
                            getString(R.string.dialog_title_gps_turned_off),
                            getString(R.string.dialog_message_last_known_location)
                        )
                    }
                }
            } else {
                showRationaleDialog()
            }
        }
    }

    private val onLocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            context?.let {
                getAddress(it, location)
            }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun getAddress(context: Context, location: Location) {
        val geoCoder = Geocoder(context)
        Thread {
            try {
                val addresses = geoCoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    Constants.MAX_RESULT
                )
                binding.floatingActionButton.post {
                    showAddressDialog(addresses.first().getAddressLine(0))
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun showAddressDialog(address: String) {
        activity?.let {
            DialogAlert(it, getString(R.string.dialog_address_title), address)
                .showDialogAlertWithPositiveButton(
                    R.string.dialog_address_get_apteka,
                    R.string.dialog_button_close
                ) { openMapFragment() }
        }
    }

    private fun openDetailsFragment(cost: MedicineCost) {
        with(requireActivity().supportFragmentManager) {
            beginTransaction()
                .add(
                    R.id.container,
                    DetailsFragment.newInstance(Bundle().apply {
                        putParcelable(
                            DetailsFragment.BUNDLE_EXTRA,
                            cost
                        )
                    })
                )
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }

    private fun openMapFragment() {
        with(requireActivity().supportFragmentManager) {
            beginTransaction()
                .add(
                    R.id.container,
                    MapsFragment()
                )
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }

    private fun showRationaleDialog() {
        activity?.let {
            DialogAlert(it, R.string.dialog_rationale_title, R.string.dialog_message)
                .showDialogAlertWithPositiveButton(
                    R.string.dialog_give_access,
                    R.string.dialog_decline
                ) { requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION) }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getLocation()
            } else {
                showDialog(
                    getString(R.string.dialog_title_no_gps),
                    getString(R.string.dialog_message_no_gps)
                )
            }
        }

    private fun showDialog(title: String, message: String) {
        activity?.let {
            DialogAlert(it, title, message)
                .showBaseDialogAlert(R.string.dialog_decline)
        }
    }

    private fun loadListOfPharma() {
        with(requireActivity()) {
            isDataSetAptekaRu = getPreferences(Context.MODE_PRIVATE)
                .getBoolean(Constants.IS_APTEKA_RU_KEY, true)
        }
    }

    private fun saveListOfPharma() {
        with(requireActivity()) {
            getPreferences(Context.MODE_PRIVATE).edit {
                putBoolean(Constants.IS_APTEKA_RU_KEY, isDataSetAptekaRu)
                apply()
            }
        }
    }

    private fun changeAptekaDataSet() {
        isDataSetAptekaRu = !isDataSetAptekaRu
        showAptekaDataSet()
    }

    private fun showAptekaDataSet() {
        if (isDataSetAptekaRu) {
            viewModel.getPharmaFromLocalAptekaRu()
            binding.floatingActionButton.setImageResource(R.drawable.apteka_april)
        } else {
            viewModel.getPharmaFromLocalAptekaApril()
            binding.floatingActionButton.setImageResource(R.drawable.apteka_ru)
        }
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.Success -> {
                binding.includeLoadingLayout.loadingLayout.hide()
                adapter.setPharma(data.pharmaData)
            }
            is AppState.Loading -> {
                binding.includeLoadingLayout.loadingLayout.show()
            }
            is AppState.Error -> {
                binding.includeLoadingLayout.loadingLayout.hide()
                binding.floatingActionButton.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload)
                ) {
                    if (isDataSetAptekaRu) {
                        viewModel.getPharmaFromLocalAptekaRu()
                    } else {
                        viewModel.getPharmaFromLocalAptekaApril()
                    }
                }
            }
        }
    }
}