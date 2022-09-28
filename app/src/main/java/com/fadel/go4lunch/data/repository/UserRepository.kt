package com.fadel.go4lunch.data.repository

import android.util.Log
import com.fadel.go4lunch.data.UserData
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
            .orderBy("userName")
            .addSnapshotListener { value, _ ->
                val workmates: List<UserData> = try {
                    value?.toObjects() ?: emptyList()
                } catch (e : Exception) {
                    e.printStackTrace()
                    emptyList()
                }
                trySend(workmates)
                Log.e("Liste des workmates: ", workmates.toString())
            }
        awaitClose { registration.remove() }
    }

    fun putUserChoice(restaurantChoice: String, userId: String, restaurantName: String) {
        val restaurantInfo = mutableMapOf<String, Any>(
            "restaurantChoose" to restaurantChoice,
            "restaurantName" to restaurantName
        )

        firestore.collection("users").document(userId).update(restaurantInfo)
    }


}