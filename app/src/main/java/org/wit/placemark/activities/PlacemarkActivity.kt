package org.wit.placemark.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CalendarView
import kotlinx.android.synthetic.main.main_layout.*
import kotlinx.android.synthetic.main.main_layout.description
import kotlinx.android.synthetic.main.main_layout.placemarkTitle
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.placemark.R
import org.wit.placemark.adapters.ImageAdapter
import org.wit.placemark.helpers.readImage
import org.wit.placemark.helpers.readImageFromPath
import org.wit.placemark.helpers.showImagePicker
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.Location
import org.wit.placemark.models.PlacemarkModel
import org.wit.placemark.models.UserModel

class PlacemarkActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp

    var user  = UserModel()
    var placemark = PlacemarkModel()

    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2

    var location = Location(52.245696, -7.139102, 15f)

    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        app = application as MainApp
        user = app.user

        dateVisited.visibility = View.INVISIBLE

        if (intent.hasExtra("placemark_edit")) {
            edit = true

            placemark = intent.extras?.getParcelable<PlacemarkModel>("placemark_edit")!!

            placemarkTitle.setText(placemark.title)
            description.setText(placemark.description)

            val imgAdp = ImageAdapter(placemark.image_list, this)
            placemarkImage.adapter = imgAdp

            val newLat = placemark.location.lat
            val newLng = placemark.location.lng

            lat.setText(newLat.toString())
            lng.setText(newLng.toString())
        }

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

        placemarkLocation.setOnClickListener {
            startActivityForResult(intentFor<MapActivity>().putExtra("location", location), LOCATION_REQUEST)
        }

        checkBox.setOnClickListener {
            if(checkBox.isChecked) {
                dateVisited.visibility = View.VISIBLE
            } else {
                dateVisited.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_placemark, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> finish()
            R.id.logout -> toast("Loggin out")
            R.id.btnAdd -> {
                placemark.title = placemarkTitle.text.toString()
                placemark.description = description.text.toString()
                val x = findViewById<CalendarView>(R.id.dateVisited)

                val newLat = placemark.location.lat
                val newLng = placemark.location.lng

                lat.setText(newLat.toString())
                lng.setText(newLng.toString())

                if (placemark.title.isEmpty()) {
                    toast(R.string.enter_placemark_title)
                    toast(x.date.toString())
                } else {
                    if (edit) {
                        app.placemarks.update(user, placemark.copy())
                    } else {
                        app.placemarks.create(user, placemark.copy())
                    }
                }
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }
        }
        return super.onOptionsItemSelected(item!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    placemark.image_list += data.data.toString()
                    placemarkImage.adapter = ImageAdapter(placemark.image_list, this)
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    location = data.extras?.getParcelable<Location>("location")!!
                    lat.setText(location.lat.toString())
                    lng.setText(location.lng.toString())
                }
            }
        }
    }
}

