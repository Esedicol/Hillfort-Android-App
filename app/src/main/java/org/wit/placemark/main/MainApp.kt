package org.wit.placemark.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.wit.placemark.models.PlacemarkJSONStore
import org.wit.placemark.models.UserStore
import org.wit.placemark.models.UserModel

class MainApp : Application(), AnkoLogger {

    lateinit var users : UserStore
    lateinit var currentUser : UserModel

    override fun onCreate() {
        super.onCreate()
        users = PlacemarkJSONStore(applicationContext)
    }
}