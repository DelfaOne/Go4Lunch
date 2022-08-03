package com.fadel.go4lunch.data.repository

import com.fadel.go4lunch.data.UserData
import com.fadel.go4lunch.data.UserGoingToRestaurantData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

//Test registration.remove
class UserRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    fun getWorkmates(): Flow<List<UserData>> = callbackFlow {
        val registration = firestore.collection("users")
            .addSnapshotListener { value, _ ->
                val workmates: List<UserData> = value?.toObjects() ?: emptyList()
                trySend(workmates)
            }
        awaitClose { registration.remove() }
    }

    // TODO Fadel add "CREATE UserGoingToRestaurantData" function

    fun getWorkmatesGoingToRestaurantToday(): Flow<List<UserGoingToRestaurantData>> = callbackFlow {
        // TODO FADEL Attaquer la route qui défini la journée côté user -> restaurant
        val registration = firestore.collection("users")
            .addSnapshotListener { value, _ ->
                val workmates: List<UserGoingToRestaurantData> = value?.toObjects() ?: emptyList()
                trySend(workmates)
            }
        awaitClose { registration.remove() }
    }


}