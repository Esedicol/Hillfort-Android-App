package org.wit.placemark.Auth

import android.content.Intent
import android.os.Bundle
import android.os.UserManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.register_page.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.placemark.R
import org.wit.placemark.activities.InitialActivity
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.UserModel

class RegisterActivity : AppCompatActivity() {

    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_page)

        app = application as MainApp

        regButton.setOnClickListener {

            val name = regName.text.toString()
            val email = regEmail.text.toString()
            val password = regPassword.text.toString()


            // 1) Check if user exist using (findByEmail) function
            val result = app.placemarks.findByEmail(email)

            if(result == null) {
                if(name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                    val user = UserModel()

                    user.name = name
                    user.email = email
                    user.password = password

                    app.placemarks.createUser(user)
                    toast(user.name)
                    startActivity(intentFor<LoginActivity>())
                    finish()
                } else {
                    toast("!! ERROR EMPTY INPUTS !!")
                }
            }
            // 2) add user
        }

        back.setOnClickListener {
            startActivity(intentFor<InitialActivity>())
            finish()
        }
    }
}