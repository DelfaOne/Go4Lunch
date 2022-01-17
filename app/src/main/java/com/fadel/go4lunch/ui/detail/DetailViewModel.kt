package com.fadel.go4lunch.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    //Recup l'argument id du restau
    public val restaurantId = savedStateHandle.get<String>("id")
}