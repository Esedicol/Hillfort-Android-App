package org.wit.placemark.Auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.register_page.*
import kotlinx.android.synthetic.main.register_page.back
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.placemark.R
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.UserModel

class RegisterActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_page)

        app = application as MainApp


        // When Register Button is Pressed //
        regButton.setOnClickListener {

            val name = regName?.text.toString()
            val email = regEmail?.text.toString()
            val password = regPassword?.text.toString()

            if (name == "" && email == "" && password == "") {
                toast("!! ERROR EMPTY INPUTS !!")
            } else {
                if (app.isEmailValid(email)) {
                    user.name = name
                    user.email = email
                    user.password = password

                    app.users.createUser(user.copy())
                    toast("New User created: ${user.name}")

                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
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
}