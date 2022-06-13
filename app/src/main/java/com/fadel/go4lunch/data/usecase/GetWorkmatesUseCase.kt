package com.fadel.go4lunch.data.usecase

import com.fadel.go4lunch.data.UserData
import com.fadel.go4lunch.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetWorkmatesUseCase @Inject constructor(
    private val getLoggedUserUseCase: GetLoggedUserUseCase,
    private val userRepository: UserRepository
) {

    operator fun invoke(): Flow<List<UserData>> = combine(
        getLoggedUserUseCase.invoke(),
        userRepository.getWorkmates()
    ) { loggedUser, workmates ->
        workmates.filter {
            it.uid == loggedUser.uid
        }
    }
}