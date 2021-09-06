package com.example.comparepharma.view

import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.comparepharma.databinding.MapsFragmentBinding
import com.example.comparepharma.utils.Constants

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.io.IOException

import com.example.comparepharma.R

class MapsFragment : Fragment() {

    private var _binding: MapsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var map: GoogleMap
    private val markers: ArrayList<Marker> = arrayListOf()

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        val initialPlace =
            LatLng(
                getString(R.string.latitude_ulyanovsk).toDouble(),
                getString(R.string.longitude_ulyanovsk).toDouble()
            )
        googleMap.addMarker(
            MarkerOptions().position(initialPlace).title(getString(R.string.marker_ulyanovsk))
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(initialPlace))
        googleMap.setOnMapLongClickListener { latLng ->
            getAddressAsync(latLng)
            addMarkerToArray(latLng)
            drawLine()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MapsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        initSearchByAddress()
    }

    private fun getAddressAsync(location: LatLng) {
        context?.let {
            val geoCoder = Geocoder(it)
            Thread {
                try {
                    val addresses = geoCoder.getFromLocation(
                        location.latitude,
                        location.longitude,
                        Constants.MAX_RESULT
                    )
                    with(binding) {
                        textAddress.post {
                            textAddress.text = addresses.firstOrNull()?.getAddressLine(0)
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }.start()
        }
    }

    private fun addMarkerToArray(location: LatLng) {
        val marker = setMarker(location, markers.size.toString(), R.drawable.location_marker)
        marker?.let {
            markers.add(marker)
        }
    }

    private fun setMarker(location: LatLng, searchText: String, resourceId: Int): Marker? {
        return map.addMarker(
            MarkerOptions().position(location).title(searchText).icon(
                BitmapDescriptorFactory.fromResource(resourceId)
            )
        )
    }

    private fun drawLine() {
        val last: Int = markers.size - 1
        if (last >= 1) {
            val prev: LatLng = markers[last - 1].position
            val cur: LatLng = markers[last].position
            map.addPolyline(
                PolylineOptions()
                    .add(prev, cur)
                    .color(Color.RED)
                    .width(Constants.WIDTH_MAP_LINE)
            )
        }
    }

    private fun initSearchByAddress() {
        binding.buttonSearch.setOnClickListener {
            val geoCoder = Geocoder(it.context)
            val searchText = binding.searchAddress.text.toString()
            Thread {
                try {
                    val addresses = geoCoder.getFromLocationName(searchText, Constants.MAX_RESULT)
                    if (addresses.isNotEmpty()) {
                        goToAddress(addresses, it, searchText)
                    }
                } catch (ex: IOException) {
                    ex.printStackTrace()
                }
            }.start()
        }
    }

    private fun goToAddress(
        addresses: MutableList<Address>,
        view: View,
        searchText: String
    ) {
        val location = LatLng(
            addresses.first().latitude,
            addresses.first().longitude
        )
        view.post {
            setMarker(location, searchText, R.drawable.red_flag_ico)
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    location,
                    Constants.ZOOM_MAP
                )
            )
        }
    }
}