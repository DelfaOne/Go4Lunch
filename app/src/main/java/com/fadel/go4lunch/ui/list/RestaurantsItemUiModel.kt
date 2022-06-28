package com.fadel.go4lunch.ui.list

import com.fadel.go4lunch.utils.EquatableCallback

data class RestaurantsItemUiModel(
    val name: String,
    val address: String,
    val photo: String?,
    val openingHours: String,
    val distance: String,
    val interestNumber: String,
    val numberOfStars: Float,
    val onItemClicked: EquatableCallback?
)
