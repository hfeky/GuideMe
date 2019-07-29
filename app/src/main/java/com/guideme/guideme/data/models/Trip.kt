package com.guideme.guideme.data.models

data class Trip(
    val name: String,
    val description: String,
    val photo: String? = null,
    val tags: List<String>,
    val places: List<Place>
)
