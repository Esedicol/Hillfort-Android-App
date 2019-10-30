package org.wit.placemark.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_placemark.*
import kotlinx.android.synthetic.main.activity_placemark.description
import kotlinx.android.synthetic.main.activity_placemark.placemarkTitle
import kotlinx.android.synthetic.main.card_placemark.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.placemark.R
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

        if (intent.hasExtra("placemark_edit")) {
            edit = true

            placemark = intent.extras?.getParcelable<PlacemarkModel>("placemark_edit")!!

            placemarkTitle.setText(placemark.title)
            description.setText(placemark.description)
            placemarkImage.setImageBitmap(readImageFromPath(this, placemark.image))
        }

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

        placemarkLocation.setOnClickListener {
            startActivityForResult(intentFor<MapActivity>().putExtra("location", location), LOCATION_REQUEST)
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

            }
//            R.id.btnAdd -> {
//                placemark.title = placemarkTitle.text.toString()
//                placemark.description = description.text.toString()
//
//                if (placemark.title.isEmpty()) {
//                    toast(R.string.enter_placemark_title)
//                } else {
//                    if (edit) {
//                        app.placemarks.update(placemark.copy())
//                    } else {
//                        app.placemarks.create(placemark.copy())
//                    }
//                }
//                setResult(AppCompatActivity.RESULT_OK)
//                finish()
//            }
        }
        return super.onOptionsItemSelected(item!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    placemark.image = data.getData().toString()
                    placemarkImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_placemark_image)
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    location = data.extras?.getParcelable<Location>("location")!!
                }
            }
        }
    }
}

