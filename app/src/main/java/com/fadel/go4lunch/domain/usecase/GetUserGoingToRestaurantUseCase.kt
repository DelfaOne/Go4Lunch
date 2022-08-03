package com.fadel.go4lunch.domain.usecase

import com.fadel.go4lunch.data.repository.UserRepository
import com.fadel.go4lunch.domain.entity.UserGoingToRestaurantEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUserGoingToRestaurantUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(): Flow<List<UserGoingToRestaurantEntity>> =
        userRepository.getWorkmatesGoingToRestaurantToday().map { users ->
            users.mapNotNull { userGoingToRestaurantData ->
                UserGoingToRestaurantEntity(
                    uid = userGoingToRestaurantData.uid ?: return@mapNotNull null,
                    userName = userGoingToRestaurantData.userName ?: return@mapNotNull null,
                    avatarUrl = userGoingToRestaurantData.avatarUrl,
                    email = userGoingToRestaurantData.email,
                    restaurantName = userGoingToRestaurantData.restaurantName
                        ?: return@mapNotNull null,
                )
            }
        }
}