package com.fadel.go4lunch.domain.workmates.usecase

import com.fadel.go4lunch.data.UserData
import com.fadel.go4lunch.data.repository.UserRepository
import com.fadel.go4lunch.data.usecase.GetLoggedUserUseCase
import com.fadel.go4lunch.domain.workmates.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetWorkmatesUseCase @Inject constructor(
    private val getLoggedUserUseCase: GetLoggedUserUseCase,
    private val userRepository: UserRepository
) {

    operator fun invoke(): Flow<List<UserEntity>> = combine(
        getLoggedUserUseCase.invoke(),
        userRepository.getWorkmates()
    ) { loggedUser, workmates: List<UserData> ->
        workmates.filter {
            it.uid != loggedUser.uid
        }.mapNotNull {
            UserEntity(
                it.uid ?: return@mapNotNull null,
                it.userName ?: return@mapNotNull null,
                it.restaurantChoose ?: return@mapNotNull null,
                it.restaurantName ?: return@mapNotNull null,
                it.avatarUrl,
                it.email ?: return@mapNotNull null
            )
        }
    }
}