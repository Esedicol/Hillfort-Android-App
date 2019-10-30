package org.wit.placemark.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import org.jetbrains.anko.intentFor
import org.wit.placemark.R
import org.wit.placemark.Auth.InitialActivity
import org.wit.placemark.main.MainApp


class SplashScreen : AppCompatActivity() {

    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({ startActivity(intentFor<InitialActivity>())
            finish()
        }, 3000)
    }
}
