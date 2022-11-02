package com.fadel.go4lunch.ui.login

import androidx.lifecycle.ViewModel
import com.fadel.go4lunch.data.repository.UserRepository
import com.fadel.go4lunch.utils.SingleLiveEvent
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userRepository: UserRepository
) : ViewModel() {

    val navigationOrder = SingleLiveEvent<NavigationOrder>()

    init {
        if (firebaseAuth.currentUser != null) {
            navigationOrder.value = NavigationOrder.ToMainActivity
        }
    }


    sealed class NavigationOrder {
        object ToMainActivity : NavigationOrder()
    }

}