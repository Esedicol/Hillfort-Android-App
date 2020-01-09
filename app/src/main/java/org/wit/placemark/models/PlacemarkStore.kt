package org.wit.placemark.models

import com.google.firebase.auth.FirebaseUser

interface PlacemarkStore {

    fun findAllHillforts(): List<HillfortModel>?
    fun findOneHillfort(hillfortID: Int): HillfortModel?
    fun findHillfortsByName(name:String): ArrayList<HillfortModel>?

    fun createHillfort(hillfort: HillfortModel)
    fun deleteHillfort(hillfort: HillfortModel)
    fun updateHillfort(hillfort: HillfortModel)

    fun toggleFavourite(hillfort: HillfortModel)
    fun sortedByFavourite(): List<HillfortModel>?
    fun deleteUser(user:FirebaseUser)
    fun logout()
}