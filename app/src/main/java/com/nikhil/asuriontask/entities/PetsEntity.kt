package com.nikhil.asuriontask.entities

import android.os.Parcel
import android.os.Parcelable

data class PetsEntity(
    val content_url: String?,
    val date_added: String?,
    val image_url: String?,
    val title: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<PetsEntity> {
        override fun createFromParcel(parcel: Parcel): PetsEntity {
            return PetsEntity(parcel)
        }

        override fun newArray(size: Int): Array<PetsEntity?> {
            return arrayOfNulls(size)
        }
    }
}