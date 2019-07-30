package com.guideme.guideme.data.models

import android.os.Parcel
import android.os.Parcelable

data class TourGuide(
    val name: String,
    val phoneNumber: String,
    val photo: String,
    val rating: Double,
    val origin: String,
    val perks: List<String>,
    val activities: List<String>
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        parcel.createStringArrayList()!!)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(phoneNumber)
        parcel.writeString(photo)
        parcel.writeDouble(rating)
        parcel.writeString(origin)
        parcel.writeStringList(perks)
        parcel.writeStringList(activities)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TourGuide> {
        override fun createFromParcel(parcel: Parcel): TourGuide {
            return TourGuide(parcel)
        }

        override fun newArray(size: Int): Array<TourGuide?> {
            return arrayOfNulls(size)
        }
    }
}
