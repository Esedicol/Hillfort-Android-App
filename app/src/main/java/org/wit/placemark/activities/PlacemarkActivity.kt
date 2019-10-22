package org.wit.placemark.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.placemark.R
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.PlacemarkModel


class PlacemarkActivity : AppCompatActivity(), AnkoLogger {
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        app = application as MainApp

        btnAdd.setOnClickListener {
            val title = placemarkTitle.text.toString()
            val description = description.text.toString()

            val pm = PlacemarkModel(title, description)

            if (title.isNotEmpty() || description.isNotEmpty()) {
                app.placemarks.add(pm)
                toast("Adding New Placemark: ${pm.title}")
                for (i in app.placemarks.indices) {
                    println("Placemark[$i]: Title: ${app.placemarks[i].title}, Description: ${app.placemarks[i].description} ${this.app.placemarks[i]}")
                }
            }
            else {
                toast ("Please Enter a title")
            }
        }
    }
}