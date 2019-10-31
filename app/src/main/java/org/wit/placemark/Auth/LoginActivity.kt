package org.wit.placemark.Auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.login_page.*
import kotlinx.android.synthetic.main.register_page.back
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.placemark.R
import org.wit.placemark.activities.PlacemarkListActivity
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.UserModel
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        app = application as MainApp

        loginButton.setOnClickListener {
            val email = loginEmail!!.text.toString()
            val password = loginPassword!!.text.toString()

            if (email == "" || password == "") {
                toast(" !! ERROR EMPTY INPUTS !!")
            } else {
                if (app.isEmailValid(email)) {

                    try {
                        val user: UserModel? = app.users.findUserByEmail(email)

                        if (user != null && user.password == password) {
                            toast("HELLO ${user.name} :)")

                            // We set the currentUser to be the currentUser who logged in
                            app.currentUser = user

                            startActivity(
                                Intent(
                                    this@LoginActivity,
                                    PlacemarkListActivity::class.java
                                )
                            )
                        } else {
                            toast("!! ERROR USER NOT FOUND !!")
                        }
                    } catch (e: Exception) {
                        toast("!! ERROR LOGING IN !!")
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
}