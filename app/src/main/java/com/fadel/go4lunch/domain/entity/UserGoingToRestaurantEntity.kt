package com.fadel.go4lunch.domain.entity

data class UserGoingToRestaurantEntity(
    val uid: String,
    val userName: String,
    val avatarUrl: String?,
    val email: String?,
    val restaurantName: String
)