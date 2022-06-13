package com.fadel.go4lunch.ui.detail


data class RestaurantDetailUiModel(
    val id: String,
    val name: String,
    val address: String,
    val imageUrl: String,
    val rating: Float,
    val phoneNumber: String,
    val website: String
)
