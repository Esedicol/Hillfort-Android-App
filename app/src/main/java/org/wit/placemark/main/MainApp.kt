package org.wit.placemark.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.wit.placemark.models.PlacemarkJSONStore
import org.wit.placemark.models.UserStore
import org.wit.placemark.models.UserModel

class MainApp : Application(), AnkoLogger {

    lateinit var users : UserStore
    lateinit var currentUser : UserModel

    val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

    override fun onCreate() {
        super.onCreate()
        users = PlacemarkJSONStore(applicationContext)
    }

    fun isEmailValid(email: String): Boolean {
        return emailRegex.toRegex().matches(email);
    }
}