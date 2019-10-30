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

    val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        app = application as MainApp

        loginButton.setOnClickListener {
            val email = loginEmail.text.toString()
            val password = loginPassword.text.toString()

            if (email == "" || password == "") {
                toast(" !! ERROR EMPTY INPUTS !!")
            } else {
                // check if Email format is valid before login
                if (isEmailValid(email)) {
                    val user = app.placemarks.findUserByEmail(email)

                        if (user != null && user.password == password) {
                            toast("LOGGING IN ......")

                            // We set the user to be the user who logged in
                            app.user = user

                            startActivity(intentFor<PlacemarkActivity>())
                            finish()

                        } else {
                            toast("!! ERROR INVALID INPUTS !!")
                        }
                } else {
                    toast("Invalid email format !!")
                }
            }

            back.setOnClickListener {
                startActivity(intentFor<InitialActivity>())
                finish()
            }
        }
    }

        fun isEmailValid(email: String): Boolean {
            return emailRegex.toRegex().matches(email);
        }
}