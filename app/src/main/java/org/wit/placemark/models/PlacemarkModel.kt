package org.wit.placemark.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlacemarkModel(var id : Long = 0,
                          var title : String = "",
                          var description : String = "",
                          var location : Location = Location(),
                          var note : String = "",
                          var image_list : List<String> = ArrayList(),  // Lists to display multiple images
                          var check_box : Boolean = false,
                          var dateVisited : String = ""

): Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable


