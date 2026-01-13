package com.example.assignment1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.assignment1.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_map, container, false)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.mapFragment_FCV_map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        return v
    }

    override fun onMapReady(map: GoogleMap) {
        this.googleMap = map
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))
    }

    fun zoomToLocation(lat: Double, lon: Double) {
        val location = LatLng(lat, lon)
        if (::googleMap.isInitialized) {
            googleMap.clear() // Optional: clear old markers
            googleMap.addMarker(MarkerOptions().position(location))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
        }
    }
}