package com.fadel.go4lunch.ui.list

data class RestaurantsItemUiModel(
    val name: String,
    val address: String,
    val photo: String?,
    val openingHours: String,
    val distance: String,
    val interestNumber: String,
    val numberOfStars: Double,
    val onItemClicked: (() -> Unit)?
)
