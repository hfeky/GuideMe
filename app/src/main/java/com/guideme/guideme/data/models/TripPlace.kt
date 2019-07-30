package com.guideme.guideme.data.models

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class TripPlace(
    val placeId: String,
    val name: String,
    val description: String? = "No description available.",
    val order: Int
) : Parcelable {

    lateinit var date: Date

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(placeId)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(order)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TripPlace> {
        override fun createFromParcel(parcel: Parcel): TripPlace {
            return TripPlace(parcel)
        }

        override fun newArray(size: Int): Array<TripPlace?> {
            return arrayOfNulls(size)
        }
    }
}
