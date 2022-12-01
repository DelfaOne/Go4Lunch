package com.fadel.go4lunch.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class UserSearchRepository {

    private val searchResultLiveData = MutableLiveData<String>()

    fun usersSearch(restaurantId: String) {
        searchResultLiveData.value = restaurantId
    }
}