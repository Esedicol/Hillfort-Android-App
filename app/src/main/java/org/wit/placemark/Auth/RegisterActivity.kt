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
    lateinit var user : UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_page)

        app = application as MainApp

        regButton.setOnClickListener {
            toast("Register Button Pressed")

            user.name = regName.text.toString()
            user.email = regEmail.text.toString()
            user.password = regPassword.text.toString()

            // 1) Check if user exist
            // 2) add user
        }

        back.setOnClickListener {
            startActivity(intentFor<InitialActivity>())
            finish()
        }
    }
}