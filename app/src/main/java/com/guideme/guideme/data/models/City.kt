package com.guideme.guideme.data.models

data class City(
    val cityId: String,
    val name: String,
    val trips: ArrayList<Trip>
)
