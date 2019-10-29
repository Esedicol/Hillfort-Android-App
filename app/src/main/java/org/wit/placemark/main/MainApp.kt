package org.wit.placemark.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.placemark.models.PlacemarkJSONStore
import org.wit.placemark.models.Store

class MainApp : Application(), AnkoLogger {

    lateinit var placemarks: Store

    override fun onCreate() {
        super.onCreate()
        placemarks = PlacemarkJSONStore(applicationContext)
        info("Placemark started")
    }
}