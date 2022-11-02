package com.fadel.go4lunch.ui.detail

import android.app.Application
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.fadel.go4lunch.BuildConfig
import com.fadel.go4lunch.R
import com.fadel.go4lunch.data.repository.NearbyPlacesRepo
import com.fadel.go4lunch.data.repository.UserRepository
import com.fadel.go4lunch.domain.workmates.usecase.GetWorkmatesUseCase
import com.fadel.go4lunch.ui.workmates.WorkmateUiModelConverter
import com.fadel.go4lunch.utils.DispatcherProvider
import com.fadel.go4lunch.utils.EquatableCallback
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
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
    private val userRepository: UserRepository,
    private val context: Application
) : ViewModel() {


    fun getViewStateLiveData(): LiveData<RestaurantDetailUiModel> =
        liveData(dispatcherProvider.ioDispatcher) {
            combine(
                flowOf(
                    nearbyPlacesRepo.getDetailResult(
                        savedStateHandle.get<String>(DetailFragment.KEY_PLACE_ID)!!,
                        BuildConfig.GMP_KEY
                    )?.result
                ).onEach {
                    Log.d("DetailViewModel", "detail result : $it")
                },
                getWorkmatesUseCase.invoke().onEach {
                    Log.d("DetailViewModel", "getWorkmatesUseCase result : $it")
                }
            ) { detailResult, workmates ->
                if (detailResult?.placeId != null && detailResult?.name != null && detailResult.formattedAddress != null && detailResult.photos != null && detailResult.rating != null) {
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
                                onRestaurantChooseClicked = EquatableCallback {
                                    viewModelScope.launch(dispatcherProvider.ioDispatcher) {
                                        val currentUser = firebaseAuth.currentUser
                                        if (currentUser != null) {
                                            userRepository.putUserChoice(
                                                detailResult.placeId,
                                                currentUser.uid,
                                                detailResult.name
                                            )
                                        }
                                    }
                                },
                                onWebsiteClicked = EquatableCallback {
                                    if (detailResult.website != null) {
                                        ContextCompat.startActivity(
                                            context,
                                            Intent(
                                                Intent.ACTION_VIEW,
                                                Uri.parse(detailResult.website)
                                            ).apply {
                                                    flags = FLAG_ACTIVITY_NEW_TASK
                                            },
                                            null
                                        )
                                    }
                                },
                                onPhoneClicked = EquatableCallback {
                                    if (detailResult.formattedPhoneNumber != null) {
                                        ContextCompat.startActivity(
                                            context,
                                            Intent(Intent.ACTION_DIAL).apply {
                                                data = Uri.parse("tel:${detailResult.formattedPhoneNumber}")
                                                flags = FLAG_ACTIVITY_NEW_TASK
                                            },
                                            null
                                        )
                                    }
                                },
                                buttonChoiceColor =
                                if (currentWorkmate?.restaurantChoose == detailResult.placeId) {
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
            }.collect()
        }

    private fun buildPhotoUrl(photoReference: String) =
        "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=$photoReference&key=${BuildConfig.GMP_KEY}"

}


