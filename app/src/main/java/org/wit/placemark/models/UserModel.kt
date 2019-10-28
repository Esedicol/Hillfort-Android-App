package org.wit.placemark.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel (
    var id: Long = 0,
    var fName: String = "",
    var lName: String = "",
    var userName: String = "",
    var email: String = "",
    var password: String = "") : Parcelable