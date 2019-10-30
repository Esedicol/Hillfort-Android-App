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
import org.wit.placemark.helpers.showImagePicker
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.Location
import org.wit.placemark.models.PlacemarkModel
import org.wit.placemark.models.UserModel
import java.text.SimpleDateFormat

class PlacemarkActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp

    var user  = UserModel()
    var placemark = PlacemarkModel()

    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2

    var location = Location(52.245696, -7.139102, 15f)
    var date = String()

    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        app = application as MainApp
        user = app.currentUser

        // Hide Data Picker till Visted box is ticked
        dateVisited.visibility = View.INVISIBLE

        if (intent.hasExtra("placemark_edit")) {
            edit = true
            toast("!! WARNING YOU ARE NOW IN EDIT MODE !!")

            placemark = intent.extras?.getParcelable<PlacemarkModel>("placemark_edit")!!

            placemarkTitle.setText(placemark.title)
            description.setText(placemark.description)

            val imgAdp = ImageAdapter(placemark.image_list, this)
            placemarkImage.adapter = imgAdp

            checkBox.isChecked = placemark.check_box

            val dateFormat = SimpleDateFormat("dd-mm-yyyy")
            val dateSelected = dateFormat.format(dateVisited.date)
            date = dateSelected

            if (placemark.location.lng != -7.139102 && placemark.location.lat != 52.245696) {
                val newLng = "%.6f".format(placemark.location.lng.toString())
                val newLat = "%.6f".format(placemark.location.lat.toString())

                lng.text = "Lng:  ${newLng}"
                lat.text = "Lat:  ${newLat}"
            }
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
                placemark.check_box = true
            } else {
                dateVisited.visibility = View.INVISIBLE
                placemark.check_box = false
            }
        }

        dateVisited.setOnDateChangeListener(CalendarView.OnDateChangeListener(){
                v, y, m, d -> date = "${d}-${m}-${y}"
            dateText.text = date
        })

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

                if (location.lat != 52.245696 && location.lng != -7.139102) {
                    placemark.location = location
                }

//                lat.setText("%.6f".format(placemark.location.lat).toString())
//                lng.setText("%.6f".format(placemark.location.lng).toString())

                placemark.check_box = checkBox.isChecked
                placemark.dateVisited = date

                if (placemark.title.isEmpty()) {
                    toast(R.string.enter_placemark_title)
                } else {
                    if (edit) {
                        app.users.update(user, placemark.copy())
                    } else {
                        app.users.create(user, placemark.copy())
                    }

                    setResult(RESULT_OK)
                    finish()
                }
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
                    lat.text = "%.6f".format(location.lat.toString())
                    lng.text = "%.6f".format(location.lng.toString())
                }
            }
        }
    }
}

