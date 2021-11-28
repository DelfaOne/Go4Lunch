package com.fadel.go4lunch.ui.workmates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WorkmatesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is workmates Fragment"
    }
    val text: LiveData<String> = _text
}