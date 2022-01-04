package com.fadel.go4lunch.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapFragment : SupportMapFragment() {


    private val vm by viewModels<MapViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //checkPermissions()
        setupMap()
    }

    @SuppressLint("MissingPermission")
    private fun setupMap() {
        getMapAsync { googleMap: GoogleMap ->
            /*val sydney = LatLng(-34.0, 151.0)
             googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
             googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/

            vm.restaurantListLiveData.observe(viewLifecycleOwner) { restaurant ->
                restaurant.forEach {
                    val positionLatLnt = LatLng(it.lat, it.long)
                    googleMap.addMarker(MarkerOptions().position(positionLatLnt))
                }
            }

            vm.viewActionSingleLiveEvent.observe(viewLifecycleOwner) {
                when (it) {
                    MapViewModel.MapViewActions.RequestLocationRestriction -> ActivityCompat.requestPermissions(
                        this.requireActivity(),
                        arrayOf(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ),
                        0
                    )
                    is MapViewModel.MapViewActions.ZoomTo -> googleMap.moveCamera(
                        CameraUpdateFactory.newCameraPosition(
                            CameraPosition.builder().target(
                                LatLng(it.lat, it.long)
                            ).zoom(13F).build()
                        )
                    )
                }
            }

            //googleMap.isMyLocationEnabled = true
            googleMap.uiSettings.isMyLocationButtonEnabled = true
        }
    }


    /*private fun checkPermissions() {
        vm.permissionListLiveData.observe(viewLifecycleOwner) {
            if (!(it.contains(Manifest.permission.ACCESS_FINE_LOCATION) || it.contains(Manifest.permission.ACCESS_COARSE_LOCATION))) {
                ActivityCompat.requestPermissions(
                    this.requireActivity(),
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    0
                )
            } else {
                setupMap()
            }
        }
    }*/
}