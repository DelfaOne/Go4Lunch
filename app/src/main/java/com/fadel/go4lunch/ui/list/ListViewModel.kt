package com.fadel.go4lunch.ui.list

import androidx.lifecycle.*
import com.fadel.go4lunch.data.repository.LocationRepository
import com.fadel.go4lunch.data.repository.NearbyPlacesRepo
import com.fadel.go4lunch.utils.DispatcherProvider
import com.fadel.go4lunch.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val nearbyPlacesRepository: NearbyPlacesRepo,
    private val locationRepository: LocationRepository,
    dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val navigationOrder = SingleLiveEvent<NavigationOrder>()

    val restaurantListLiveData: LiveData<List<RestaurantsItemUiModel>> =
        liveData(dispatcherProvider.ioDispatcher) {

            locationRepository.getLocationFlow().flatMapLatest {
                flowOf(
                    nearbyPlacesRepository.getNearbyResults(
                        "${it.latitude},${it.longitude}",
                        "1500",
                        "restaurant",
                        apiKey
                    )
                )
            }.collect { responses ->
                responses?.let { list ->
                    emit(list.mapNotNull {
                        val itemId = it.placeId

                        RestaurantsItemUiModel(
                            name = it.name ?: return@mapNotNull null,
                            address = it.vicinity ?: return@mapNotNull null,
                            photo = it.photos?.firstOrNull()?.photoReference,
                            openingHours = when (it.openingHours?.openNow) {
                                true -> "is Open"
                                false -> "is Closed"
                                else -> "unknown"
                            },
                            distance = "120", //TODO calcul de la distance
                            interestNumber = "3", //TODO from firestore ?
                            numberOfStars = (it.rating ?: 0.0),
                            onItemClicked = {
                                onItemClicked(itemId)
                                navigationOrder.value = NavigationOrder.Detail
                            },
                        )
                    })
                }
            }
        }

    fun onItemClicked(itemId: String?) {
        viewModelScope.launch {
            flowOf(
                itemId?.let { nearbyPlacesRepository.getDetailResult(it, apiKey) }
            ).collect { response ->

            }
        }
    }

    companion object {
        const val apiKey = "AIzaSyCod1va_8xcRFf8epc5HkFkDY1ZUu6bkeo"
    }

    sealed class NavigationOrder {
        object Detail : NavigationOrder()
    }


}