package com.fadel.go4lunch.data.usecase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class GetLoggedUserUseCase @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    operator fun invoke(): Flow<FirebaseUser> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->

            firebaseAuth.currentUser.let {
                if (it == null) {
                    Log.i(this.javaClass.name, "User firebase is null ")
                } else {
                    trySend(it)
                }
            }
        }
        firebaseAuth.addAuthStateListener(listener)
        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }
}