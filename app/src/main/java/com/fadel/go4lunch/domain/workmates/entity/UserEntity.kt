package com.fadel.go4lunch.domain.workmates.entity

data class UserEntity(
    val uid: String,
    val userName: String,
    val restaurantChoose: String?,
    val restaurantName: String?,
    val avatarUrl: String?,
    val email: String
)
