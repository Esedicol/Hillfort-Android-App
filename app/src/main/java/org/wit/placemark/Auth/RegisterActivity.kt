package org.wit.placemark.Auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.UserManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.login_page.*
import kotlinx.android.synthetic.main.register_page.*
import kotlinx.android.synthetic.main.register_page.back
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.placemark.R
import org.wit.placemark.activities.InitialActivity
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.UserModel

class RegisterActivity : AppCompatActivity() {

    val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_page)

        app = application as MainApp


        // When Register Button is Pressed //
        regButton.setOnClickListener {

            val name = regName.text.toString()
            val email = regEmail.text.toString()
            val password = regPassword.text.toString()

                if (name == "" && email == "" && password == "") {
                    toast("!! ERROR EMPTY INPUTS !!")
                } else {
                    if (isEmailValid(email)) {
                        val checkIfUserExisits = app.placemarks.findByEmail(email)

                        if (checkIfUserExisits == null) {

                            val user = UserModel()

                            user.name = name
                            user.email = email
                            user.password = password

                            app.placemarks.createUser(user)

                            toast("New user created: ${user.name}")
                            startActivity(intentFor<LoginActivity>())
                            finish()
                        }
                    } else {
                        toast("!! Invalid Email Format !!")
                    }
                }
            }


        // When Back Button is Pressed //
        back.setOnClickListener {
            startActivity(intentFor<InitialActivity>())
            finish()
        }
    }

    fun isEmailValid(email: String): Boolean {
        return emailRegex.toRegex().matches(email);
    }
}