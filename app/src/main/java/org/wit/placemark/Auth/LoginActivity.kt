package org.wit.placemark.Auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.login_page.*
import kotlinx.android.synthetic.main.register_page.back
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.placemark.R
import org.wit.placemark.activities.InitialActivity
import org.wit.placemark.activities.PlacemarkActivity
import org.wit.placemark.main.MainApp

class LoginActivity : AppCompatActivity() {

    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        app = application as MainApp

        loginButton.setOnClickListener {
            val users = app.placemarks.findAllUsers()

            val email = loginEmail.text.toString()
            val password = loginPassword.text.toString()

            if(email.isEmpty() || password.isEmpty()) {
                toast(" !! ERROR EMPTY INPUTS !!")
            } else {
                for(x in users) {
                    if(x.email == email  && x.password == password) {

                        app.user = app.placemarks.findUser(x.id)!!
                        toast("LOGGING IN ......")
                        startActivity(intentFor<PlacemarkActivity>())
                        finish()
                    } else {
                        toast("!! ERROR INVALID INPUTS !!")
                    }
                }
            }
        }

        back.setOnClickListener {
            startActivity(intentFor<InitialActivity>())
            finish()
        }
    }
}