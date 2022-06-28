package com.fadel.go4lunch.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.fadel.go4lunch.BuildConfig
import com.fadel.go4lunch.data.repository.LocationRepository
import com.fadel.go4lunch.data.repository.NearbyPlacesRepo
import com.fadel.go4lunch.utils.DispatcherProvider
import com.fadel.go4lunch.utils.EquatableCallback
import com.fadel.go4lunch.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.transformLatest
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val nearbyPlacesRepository: NearbyPlacesRepo,
    private val locationRepository: LocationRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val navigationOrder = SingleLiveEvent<NavigationOrder>()

    val restaurantListLiveData: LiveData<List<RestaurantsItemUiModel>> = liveData(dispatcherProvider.ioDispatcher) {

        locationRepository.getLocationFlow().transformLatest {
            emit(
                nearbyPlacesRepository.getNearbyResults(
                    it,
                    "1500",
                    "restaurant",
                    BuildConfig.GMP_KEY
                )
            )
        }.collect { responses ->
            responses?.let { list ->
                emit(list.mapNotNull {

                    RestaurantsItemUiModel(
                        name = it.name ?: return@mapNotNull null,
                        address = it.vicinity ?: return@mapNotNull null,
                        photo = it.photos?.firstOrNull()?.photoReference?.let {
                            "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=${it}&key=${BuildConfig.GMP_KEY}"
                        },
                        openingHours = when (it.openingHours?.openNow) {
                            true -> "is Open"
                            false -> "is Closed"
                            else -> "unknown"
                        },
                        distance = "120", //TODO calcul de la distance
                        interestNumber = "3", //from firestore
                        numberOfStars = ((it.rating ?: 0.0).toFloat()),
                        onItemClicked = it.placeId?.let {
                            EquatableCallback {
                                navigationOrder.value = NavigationOrder.Detail(it)
                            }
                        },
                    )
                })
            }
        }
    }

    sealed class NavigationOrder {
        data class Detail(
            val detailId: String
        ) : NavigationOrder()
    }


}