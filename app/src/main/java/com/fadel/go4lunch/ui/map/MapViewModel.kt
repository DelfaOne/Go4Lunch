package com.fadel.go4lunch.ui.map

import android.util.Log
import androidx.lifecycle.*
import com.fadel.go4lunch.data.repository.LocationRepository
import com.fadel.go4lunch.data.repository.NearbyPlacesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val nearbyPlacesRepository: NearbyPlacesRepo,
    private val savedStateHandle: SavedStateHandle,
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is map Fragment"
    }
    val text: LiveData<String> = _text

    val restaurantListLiveData: LiveData<List<MapUiModel>>

    init {
        Log.d(TAG, savedStateHandle["TOTO"] ?: "")
        restaurantListLiveData = liveData {

            locationRepository.getLocationFlow().flatMapLatest {
                flowOf(
                    nearbyPlacesRepository.getNearbyResults(
                        "${it.latitude},${it.longitude}",
                        "1500",
                        "restaurant",
                        "AIzaSyCod1va_8xcRFf8epc5HkFkDY1ZUu6bkeo"
                    )
                )
            }.collect { responses ->
                responses?.let { list ->
                    emit(list?.mapNotNull {
                        MapUiModel(
                            id = it.placeId ?: return@mapNotNull null,
                            name = it.name ?: return@mapNotNull null,
                            isTested = false,
                            lat = it.geometry?.location?.lat ?: return@mapNotNull null,
                            long = it.geometry?.location?.lng ?: return@mapNotNull null
                        )
                    })
                }
            }
        }
    }

    companion object {
        const val TAG = "YOLO"
    }
}