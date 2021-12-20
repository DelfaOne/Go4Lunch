package com.fadel.go4lunch.ui.map

import androidx.annotation.DrawableRes

data class MapUiModel(
    val id: String,
    val name: String,
    val isTested: Boolean,
    val lat: Double,
    val long: Double
)
