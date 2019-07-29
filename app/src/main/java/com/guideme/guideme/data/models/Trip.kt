package com.guideme.guideme.data.models

data class Trip(
    val tripId: String,
    val name: String,
    val location: String,
    val description: String,
    val photo: String? = null,
    val tags: List<String>,
    val places: List<Place>
)
