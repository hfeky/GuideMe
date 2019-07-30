package com.guideme.guideme.data

import android.util.Log
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
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

    fun addFavoritePlace(
        userId: String,
        placeId: String,
        successListener: OnSuccessListener<Void>
    ) {
        getUser(userId, OnSuccessListener { result ->
            val favoritePlaces = result["favorite_places"] as ArrayList<String>
            favoritePlaces.add(placeId)

            val userMap = mapOf(
                "name" to result["name"] as String,
                "phone_number" to result["phone_number"] as String,
                "photo" to result["photo"] as String,
                "favorite_places" to favoritePlaces
            )

            db.collection("users")
                .document(userId)
                .update(userMap)
                .addOnSuccessListener(successListener)
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }
        })
    }

    fun getCities(successListener: OnSuccessListener<QuerySnapshot>) {
        db.collection("cities")
            .get(source)
            .addOnSuccessListener(successListener)
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    fun getCity(cityId: String, successListener: OnSuccessListener<DocumentSnapshot>) {
        db.collection("cities")
            .document(cityId)
            .get(source)
            .addOnSuccessListener(successListener)
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    fun getTrips(cityId: String, successListener: OnSuccessListener<QuerySnapshot>) {
        db.collection("cities")
            .document(cityId)
            .collection("trips")
            .get(source)
            .addOnSuccessListener(successListener)
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    fun getTripPlaces(
        cityId: String,
        tripId: String,
        successListener: OnSuccessListener<QuerySnapshot>
    ) {
        db.collection("cities")
            .document(cityId)
            .collection("trips")
            .document(tripId)
            .collection("places")
            .get(source)
            .addOnSuccessListener(successListener)
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    fun getRestaurants(cityId: String, successListener: OnSuccessListener<QuerySnapshot>) {
        db.collection("cities")
            .document(cityId)
            .collection("restaurants")
            .get(source)
            .addOnSuccessListener(successListener)
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    fun getHotels(cityId: String, successListener: OnSuccessListener<QuerySnapshot>) {
        db.collection("cities")
            .document(cityId)
            .collection("hotels")
            .get(source)
            .addOnSuccessListener(successListener)
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    fun getTourGuides(successListener: OnSuccessListener<QuerySnapshot>) {
        db.collection("tour_guides")
            .get(source)
            .addOnSuccessListener(successListener)
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }
}
