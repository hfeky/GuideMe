package com.guideme.guideme.data

import android.util.Log
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.guideme.guideme.data.models.User

class DataManager {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val source = Source.DEFAULT

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    companion object {
        private const val TAG = "DataManager"
    }

    fun createNewUser(userId: String, user: User, successListener: OnSuccessListener<Void>) {
        val userMap = mapOf(
            "name" to user.name,
            "phone_number" to user.phoneNumber,
            "photo" to user.photo
        )
        db.collection("users")
            .document(userId)
            .set(userMap)
            .addOnSuccessListener(successListener)
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    fun getUser(userId: String, successListener: OnSuccessListener<DocumentSnapshot>) {
        db.collection("users")
            .document(userId)
            .get(source)
            .addOnSuccessListener(successListener)
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }
}
