package org.wit.placemark.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.placemark.R
import org.wit.placemark.models.PlacemarkModel

class PlacemarkActivity : AppCompatActivity(), AnkoLogger {

    val placemarks = ArrayList<PlacemarkModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        info("Placemark Activity started..")
        btnAdd.setOnClickListener() {
            val title = placemarkTitle.text.toString()
            val description = description.text.toString()

            if (title.isNotEmpty() || description.isNotEmpty()) {
                info("Add Button Pressed")

                val pm = PlacemarkModel(title, description)
                placemarks.add(pm)

                for(i in placemarks.indices) {
                    println("Placemark[$i]: Title: ${placemarks[i].title}, Description: ${placemarks[i].description} ${this.placemarks[i]}")
                }
            }
            else {
               toast("Please Enter a title")

            }
        }
    }
}
