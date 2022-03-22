package com.fadel.go4lunch.ui.map

import android.Manifest
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.fadel.go4lunch.data.PermissionRepository
import com.fadel.go4lunch.data.repository.LocationRepository
import com.fadel.go4lunch.data.repository.NearbyPlacesRepo
import com.fadel.go4lunch.utils.DispatcherProvider
import com.fadel.go4lunch.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
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

    val restaurantListLiveData: LiveData<List<MapUiModel>> =
        liveData(dispatcherProvider.ioDispatcher) {
            permissionRepository.permissionListFlow.collectLatest { permissions ->
                if (permissions.contains(Manifest.permission.ACCESS_COARSE_LOCATION) && permissions.contains(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
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
                            emit(list.mapNotNull {
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

    sealed class MapViewActions {
        object RequestLocationRestriction : MapViewActions()
        data class ZoomTo(val lat: Double, val long: Double) : MapViewActions()
    }

}



