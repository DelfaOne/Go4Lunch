package com.fadel.go4lunch.ui.detail

import androidx.lifecycle.*
import com.fadel.go4lunch.BuildConfig
import com.fadel.go4lunch.R
import com.fadel.go4lunch.data.repository.NearbyPlacesRepo
import com.fadel.go4lunch.data.repository.UserRepository
import com.fadel.go4lunch.domain.workmates.usecase.GetWorkmatesUseCase
import com.fadel.go4lunch.ui.workmates.WorkmateUiModelConverter
import com.fadel.go4lunch.utils.DispatcherProvider
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val dispatcherProvider: DispatcherProvider,
    private val nearbyPlacesRepo: NearbyPlacesRepo,
    private val getWorkmatesUseCase: GetWorkmatesUseCase,
    private val firebaseAuth: FirebaseAuth,
    private val workmateUiModelConverter: WorkmateUiModelConverter,
    private val userRepository: UserRepository
) : ViewModel() {

    private val detailResultFlow = flow {
        emit(
            nearbyPlacesRepo.getDetailResult(
                savedStateHandle.get<String>(DetailFragment.KEY_PLACE_ID)!!,
                BuildConfig.GMP_KEY
            )?.result
        )
    }

    fun getViewStateLiveData(): LiveData<RestaurantDetailUiModel> =
        liveData(dispatcherProvider.ioDispatcher) {
            combine(
                detailResultFlow,
                getWorkmatesUseCase.invoke()
            ) { detailResult, workmates ->
                if (detailResult?.placeId != null && detailResult?.name != null && detailResult.formattedAddress != null && detailResult.photos != null && detailResult.rating != null && detailResult.formattedPhoneNumber != null && detailResult.website != null) {
                    val currentWorkmate = workmates.find { it.uid == firebaseAuth.currentUser?.uid }
                    val firstPhotoUrl = detailResult.photos.firstOrNull()?.photoReference
                    if (firstPhotoUrl != null) {
                        emit(
                            RestaurantDetailUiModel(
                                id = detailResult.placeId,
                                name = detailResult.name,
                                address = detailResult.formattedAddress,
                                imageUrl = buildPhotoUrl(firstPhotoUrl),
                                rating = detailResult.rating.toFloat(),
                                phoneNumber = detailResult.formattedPhoneNumber,
                                website = detailResult.website,
                                buttonChoiceColor = if (currentWorkmate?.restaurantChoose == detailResult.placeId) {
                                    R.color.green
                                } else {
                                    R.color.black
                                },
                                workmatesInterested = workmates.map {
                                    workmateUiModelConverter.convertWorkmate(
                                        it
                                    )
                                }
                            )
                        )
                    }
                }
            }
        }

    fun onRestaurantChooseClicked() {
        viewModelScope.launch(dispatcherProvider.ioDispatcher) {
            val detailResult = detailResultFlow.first()
            val currentUser = firebaseAuth.currentUser
            if (detailResult?.placeId != null && detailResult.name != null && currentUser != null) {
                userRepository.putUserChoice(
                    detailResult.placeId,
                    currentUser.uid,
                    detailResult.name
                )
            }
        }
    }

    data class RestaurantSelected(
        private val rstaurantId: String,
        private val restaurantName: String
    )

    private fun buildPhotoUrl(photoReference: String) =
        "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=$photoReference&key=${BuildConfig.GMP_KEY}"

}


