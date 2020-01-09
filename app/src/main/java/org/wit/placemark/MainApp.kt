package org.wit.placemark

import android.app.Application
import com.google.firebase.auth.FirebaseUser
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.placemark.models.*
import org.wit.placemark.models.firebase.HillfortFireStore

class MainApp : Application(), AnkoLogger {

    lateinit var hillforts: PlacemarkStore
    var activeUser: FirebaseUser? = null

    override fun onCreate() {
        super.onCreate()
        info("App started")
        hillforts = HillfortFireStore(applicationContext)
    }
}