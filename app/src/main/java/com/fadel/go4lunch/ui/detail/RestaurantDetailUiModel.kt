package com.fadel.go4lunch.ui.detail

import androidx.annotation.ColorRes
import com.fadel.go4lunch.ui.workmates.WorkmatesUiModel


data class RestaurantDetailUiModel(
    val id: String,
    val name: String,
    val address: String,
    val imageUrl: String,
    val rating: Float,
    val phoneNumber: String,
    val website: String,
    @ColorRes val buttonChoiceColor: Int,
    val workmatesInterested: List<WorkmatesUiModel>
)
