package org.wit.placemark.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlacemarkModel(
    var id: Int = 0,
    var title: String = "",
    var description: String = "",
    var date: String = "",
    var location: Location = Location(),
    var note: List<String> = ArrayList(),
    var image_list: List<String> = ArrayList(),
    var check_box: Boolean = false,
    var image: String = ""
) : Parcelable

@Parcelize
data class Location(
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f
) : Parcelable




