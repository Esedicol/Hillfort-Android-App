package org.wit.placemark.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlacemarkModel(
    var id: Long = 0,
    var title: String = "",
    var description: String = "",
    var dateVisited: String = "",
    var location: Location = Location(),
    var note: ArrayList<Note> = ArrayList(),
    var image_list: ArrayList<String> = ArrayList(),
    var check_box: Boolean = false
) : Parcelable

@Parcelize
data class Location(
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f
) : Parcelable

@Parcelize
data class Note(
    var id: Int = 0,
    var title: String = "",
    var content: String = ""
) : Parcelable




