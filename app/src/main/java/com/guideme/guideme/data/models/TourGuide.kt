package com.guideme.guideme.data.models

import android.os.Parcel
import android.os.Parcelable

data class TourGuide(
    val name: String,
    val avatar: Int,
    val rating: Double,
    val places: List<String>
) : Parcelable {

    var facilities: List<String>? = null

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readDouble(),
        parcel.createStringArrayList()!!
    ) {
        facilities = parcel.createStringArrayList()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(avatar)
        parcel.writeDouble(rating)
        parcel.writeStringList(places)
        parcel.writeStringList(facilities)
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
