package org.wit.placemark.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UserModel(
    var id: Long = 0,
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var placemarks: ArrayList<PlacemarkModel> = ArrayList() ) : Parcelable

