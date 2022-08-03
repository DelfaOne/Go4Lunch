package com.fadel.go4lunch.ui.workmates

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fadel.go4lunch.domain.usecase.GetUserGoingToRestaurantUseCase
import com.fadel.go4lunch.domain.usecase.GetWorkmatesUseCase
import com.fadel.go4lunch.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class WorkmatesViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    getWorkmatesUseCase: GetWorkmatesUseCase,
    getUserGoingToRestaurantUseCase: GetUserGoingToRestaurantUseCase
) : ViewModel() {

    val workmatesListLiveData: LiveData<List<WorkmatesUiModel>> =
        combine(
            getUserGoingToRestaurantUseCase.invoke().distinctUntilChanged(),
            getWorkmatesUseCase.invoke().distinctUntilChanged()
        ) { usersGoingToRestaurant, users ->
            users.map { userData ->
                val userGoingToRestaurant = usersGoingToRestaurant.find { userData.uid == it.uid }

                if (userGoingToRestaurant != null) {
                    WorkmatesUiModel(
                        workmateId = userGoingToRestaurant.uid,
                        detail = "${userGoingToRestaurant.userName} va à ${userGoingToRestaurant.restaurantName}", // TODO FADEL getString(...)
                        photoUrl = userGoingToRestaurant.avatarUrl
                    )
                } else {
                    WorkmatesUiModel(
                        workmateId = userData.uid!!, // TODO FADEL
                        detail = "${userData.userName!!} n'a pas encore décidé", // TODO FADEL getString(...)
                        photoUrl = userData.avatarUrl
                    )
                }
            }
        }.asLiveData(dispatcherProvider.ioDispatcher)

}
