package com.fadel.go4lunch.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.fadel.go4lunch.BuildConfig
import com.fadel.go4lunch.data.repository.NearbyPlacesRepo
import com.fadel.go4lunch.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val dispatcherProvider: DispatcherProvider,
    private val nearbyPlacesRepo: NearbyPlacesRepo
) : ViewModel() {

    fun getViewStateLiveData(): LiveData<RestaurantDetailUiModel> = liveData(dispatcherProvider.ioDispatcher) {
        nearbyPlacesRepo.getDetailResult(
            savedStateHandle.get<String>(DetailActivity.KEY_PLACE_ID)!!,
            BuildConfig.GMP_KEY
        )?.result?.let {
            if (it.placeId != null && it.name != null && it.formattedAddress != null && it.photos != null && it.rating != null && it.formattedPhoneNumber != null && it.website != null) {
                val firstPhotoUrl = it.photos.firstOrNull()?.photoReference
                if (firstPhotoUrl != null) {
                    emit(
                        RestaurantDetailUiModel(
                            id = it.placeId,
                            name = it.name,
                            address = it.formattedAddress,
                            imageUrl = photo(firstPhotoUrl),
                            rating = it.rating.toFloat(),
                            phoneNumber = it.formattedPhoneNumber,
                            website = it.website
                        )
                    )
                }
            }
        }
    }

    fun photo(photoReference: String) =
        "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=$photoReference&key=${BuildConfig.GMP_KEY}"

}


