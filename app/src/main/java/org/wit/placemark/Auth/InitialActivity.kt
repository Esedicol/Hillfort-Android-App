package org.wit.placemark.Auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.initial_page.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.wit.placemark.Auth.LoginActivity
import org.wit.placemark.Auth.RegisterActivity
import org.wit.placemark.R

class InitialActivity : AppCompatActivity(), AnkoLogger{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.initial_page)

        loginPage.setOnClickListener {
            startActivity(intentFor<LoginActivity>())
            finish()
        }

        registerPage.setOnClickListener {
            startActivity(intentFor<RegisterActivity>())
            finish()
        }
    }
}