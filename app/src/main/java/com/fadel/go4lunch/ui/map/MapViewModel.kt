package com.fadel.go4lunch.ui.map

import android.Manifest
import android.location.Location
import androidx.lifecycle.*
import com.fadel.go4lunch.BuildConfig
import com.fadel.go4lunch.data.PermissionRepository
import com.fadel.go4lunch.data.repository.LocationRepository
import com.fadel.go4lunch.data.repository.NearbyPlacesRepo
import com.fadel.go4lunch.utils.DispatcherProvider
import com.fadel.go4lunch.utils.SingleLiveEvent
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val nearbyPlacesRepository: NearbyPlacesRepo,
    private val locationRepository: LocationRepository,
    private val permissionRepository: PermissionRepository,
    dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val currentCameraPositionMutableLiveData = MutableLiveData<LatLng>()

    val restaurantListLiveData: LiveData<List<MapUiModel>> = currentCameraPositionMutableLiveData.switchMap {
        liveData(dispatcherProvider.ioDispatcher) {
            val results = nearbyPlacesRepository.getNearbyResults(
                Location("").apply {
                    latitude = it.latitude
                    longitude = it.longitude
                },
                "1500",
                "restaurant",
                BuildConfig.GMP_KEY
            )

            results?.let {
                emit(
                    results.mapNotNull {
                        MapUiModel(
                            id = it.placeId ?: return@mapNotNull null,
                            name = it.name ?: return@mapNotNull null,
                            isTested = false,
                            latLng = LatLng(
                                it.geometry?.location?.lat ?: return@mapNotNull null,
                                it.geometry.location.lng ?: return@mapNotNull null
                            )
                        )
                    }
                )
            }
        }
    }

    val viewActionSingleLiveEvent = SingleLiveEvent<MapViewActions>()

    init {

        viewModelScope.launch(dispatcherProvider.ioDispatcher) {
            permissionRepository.permissionListFlow.collect { permissions ->
                withContext(dispatcherProvider.mainDispatcher) { //Back to main thread
                    viewActionSingleLiveEvent.value = if (
                        permissions.contains(Manifest.permission.ACCESS_COARSE_LOCATION)
                        && permissions.contains(Manifest.permission.ACCESS_FINE_LOCATION)
                    ) {
                        val position = locationRepository.getLocationFlow().first()
                        MapViewActions.ZoomTo(position.latitude, position.longitude)
                    } else {
                        MapViewActions.RequestLocationRestriction
                    }
                }
            }
        }
    }

    fun onCameraIdle(target: LatLng) {
        currentCameraPositionMutableLiveData.value = target
    }

    sealed class MapViewActions {
        object RequestLocationRestriction : MapViewActions()
        data class ZoomTo(val lat: Double, val long: Double) : MapViewActions()
    }

}



