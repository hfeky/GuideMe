package com.guideme.guideme.data.models

import android.os.Parcel
import android.os.Parcelable

data class Trip(
    val tripId: String,
    val cityId: String,
    val name: String,
    val location: String,
    val description: String,
    val photo: String? = null,
    val tags: List<String>,
    val places: ArrayList<TripPlace>
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.createStringArrayList()!!,
        parcel.createTypedArrayList(TripPlace)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(tripId)
        parcel.writeString(cityId)
        parcel.writeString(name)
        parcel.writeString(location)
        parcel.writeString(description)
        parcel.writeString(photo)
        parcel.writeStringList(tags)
        parcel.writeTypedList(places)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Trip> {
        override fun createFromParcel(parcel: Parcel): Trip {
            return Trip(parcel)
        }

        override fun newArray(size: Int): Array<Trip?> {
            return arrayOfNulls(size)
        }
    }
}
