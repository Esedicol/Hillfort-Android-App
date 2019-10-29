package org.wit.placemark.Auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.register_page.*
import org.jetbrains.anko.toast
import org.wit.placemark.R
import org.wit.placemark.main.MainApp

class RegisterActivity : AppCompatActivity() {

    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_page)

        regButton.setOnClickListener {
            toast("Register Button Pressed")
        }

        back.setOnClickListener {
            toast("Back Button Pressed")
        }
    }
}