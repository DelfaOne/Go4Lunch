package com.fadel.go4lunch.ui.map

import com.google.android.gms.maps.model.LatLng

data class MapUiModel(
    val id: String,
    val name: String,
    val isTested: Boolean,
    val latLng: LatLng
)
