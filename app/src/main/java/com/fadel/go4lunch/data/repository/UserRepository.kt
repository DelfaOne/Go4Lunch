package com.fadel.go4lunch.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class UserRepository {


   /* suspend fun getUsersForPlaceId(placeId : String) : List<???> {
        val result = FirebaseFirestore.getInstance().collection("toto").get().await()

        return result.toObjects()
    }

    fun getUsersForPlaceIdAsFlow(placeId : String) : Flow<List<User>> = callbackFlow {

        FirebaseFirestore.getInstance().collection("toto").addSnapshotListener { value, error ->
            trySend(value!!.toObjects<User>())
        }
    }*/

}