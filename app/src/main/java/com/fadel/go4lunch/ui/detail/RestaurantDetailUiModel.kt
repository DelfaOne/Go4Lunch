package com.fadel.go4lunch.ui.detail

import androidx.annotation.ColorRes
import com.fadel.go4lunch.ui.workmates.WorkmatesUiModel
import com.fadel.go4lunch.utils.EquatableCallback


data class RestaurantDetailUiModel(
    val id: String,
    val name: String,
    val address: String,
    val imageUrl: String,
    val rating: Float,
    val onRestaurantChooseClicked: EquatableCallback,
    val onWebsiteClicked: EquatableCallback,
    val onPhoneClicked: EquatableCallback,
    @ColorRes val buttonChoiceColor: Int,
    val workmatesInterested: List<WorkmatesUiModel>
)
