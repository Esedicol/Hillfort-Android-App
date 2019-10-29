package org.wit.placemark.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.placemark.models.PlacemarkJSONStore
import org.wit.placemark.models.Store
import org.wit.placemark.models.UserModel

class MainApp : Application(), AnkoLogger {

    lateinit var placemarks : Store
    lateinit var user : UserModel

    override fun onCreate() {
        super.onCreate()
        placemarks = PlacemarkJSONStore(applicationContext)
        info("Placemark started")
    }
}