package org.wit.placemark.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.MapsInitializer
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
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import org.wit.placemark.adapters.NotesAdapter
import org.wit.placemark.adapters.NoteListener
import org.wit.placemark.adapters.ImageListener
import java.text.DecimalFormat


class PlacemarkActivity : AppCompatActivity(), AnkoLogger, NoteListener, ImageListener {

    lateinit var app: MainApp
    var placemark = PlacemarkModel()
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2

    var location = Location(52.245696, -7.139102, 15f)
    private lateinit var google_Map: GoogleMap
    var date = String()
    var edit = false
    var dateFormat = SimpleDateFormat("dd MMM, YYYY", Locale.UK)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        app = application as MainApp

        // ------------- Select Image Button  ------------- //
        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }


        // ------------- Select Location Button  ------------- //
        with(map) {
            onCreate(null)
            getMapAsync {
                MapsInitializer.initialize(applicationContext)
            }
        }

        placemarkLocation.setOnClickListener {
            if (placemark.location.lat == 0.0 && placemark.location.lng == 0.0) {
                startActivityForResult(
                    intentFor<MapsActivity>().putExtra("location", location),
                    LOCATION_REQUEST
                )
            } else {
                startActivityForResult(
                    intentFor<MapsActivity>().putExtra(
                        "location",
                        placemark.location
                    ), LOCATION_REQUEST
                )
            }
        }


        // ------------- Check Box  ------------- //
        setDate.visibility = View.INVISIBLE
        dateText.visibility = View.INVISIBLE

        checkBox.setOnClickListener {
            if (checkBox.isChecked) {
                setDate.visibility = View.VISIBLE
                dateText.visibility = View.VISIBLE
                placemark.check_box = true
            } else {
                setDate.visibility = View.INVISIBLE
                placemark.check_box = false
            }
        }

        // ------------- Date Picker Dialog  ------------- //
        // Tutorial from https://www.youtube.com/watch?v=gollUUFBKQA //
        setDate.setOnClickListener {
            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()

                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, month)
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    date = dateFormat.format(selectedDate.time)

                    toast(date)
                    dateText.setText("Date Visited: ${date}")
                },
                now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }


        // ------------- Notes  ------------- //
        val layoutManager = LinearLayoutManager(this)
        note_view.layoutManager = layoutManager
        note_view.adapter = NotesAdapter(placemark.note, this)
        loadNotes()

        add_note.setOnClickListener {
            if (noteContent.text.toString().isNotEmpty()) {
                placemark.note += noteContent.text.toString()
                note_view.adapter = NotesAdapter(placemark.note, this)
                noteContent.setText("")
            } else {
                toast("NOTE EMPTY")
            }
        }

        // ------------- Images  ------------- //
        val imageLayoutManager = LinearLayoutManager(this)
        image_view.layoutManager = imageLayoutManager
        image_view.adapter = ImageAdapter(placemark.image_list, this)
        loadImage()

        // ------------- Edit Mode ------------- //
        if (intent.hasExtra("placemark_edit")) {
            edit = true
            toast("!! WARNING YOU ARE NOW IN EDIT MODE !!")

            placemark = intent.extras?.getParcelable("placemark_edit")!!

            placemarkTitle.setText(placemark.title)
            description.setText(placemark.description)
            checkBox.isChecked = placemark.check_box
            dateText.text = placemark.date
            loadNotes()

            if (placemark.image_list.size == 0) {
                placemarkImage.setImageResource(R.drawable.default_image)
            } else {
                placemarkImage.setImageBitmap(readImageFromPath(this, placemark.image))
            }

            location = placemark.location
            lat.setText("LAT: ${DecimalFormat("#.##").format(location.lat)}")
            lng.setText("LNG: ${DecimalFormat("#.##").format(location.lng)}")

            val latLng = LatLng(placemark.location.lat, placemark.location.lng)
            map.getMapAsync {
                setMapLocation(it, latLng)
            }
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_placemark, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> startActivity(
                Intent(
                    this@PlacemarkActivity,
                    PlacemarkListActivity::class.java
                )
            )
            R.id.btnAdd -> {
                placemark.title = placemarkTitle.text.toString()
                placemark.description = description.text.toString()
                placemark.check_box = checkBox.isChecked
                placemark.date = date
                placemark.location = location

                if (placemark.title.isEmpty() || placemark.description.isEmpty()) {
                    toast("Add Failed -> Please enter Fort details next time")
                } else {
                    if (edit) {
                        app.users.updateFort(app.currentUser, placemark)
                        toast("UPDATING ....")
                    } else {
                        try {
                            app.users.createFort(app.currentUser, placemark)
                            toast("NEW HILLFORT ADDED")
                        } catch (e: Exception) {
                            toast("FAILED TO ADD FORT")
                        }
                    }
                }
                setResult(Activity.RESULT_OK)
                finish()

                startActivity(Intent(this@PlacemarkActivity, PlacemarkListActivity::class.java))
            }
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

                    placemark.image_list += placemark.image_list
                    image_view.adapter = ImageAdapter(placemark.image_list, this)
                } else {
                    toast("ERROR IMAGE")
                }
            }

            LOCATION_REQUEST -> {
                if (data != null) {
                    location = data.extras?.getParcelable("location")!!
                    val lt = location.lat
                    val lg = location.lng

                    lat.setText("LAT: ${DecimalFormat("#.##").format(lt)}")
                    lng.setText("LNG: ${DecimalFormat("#.##").format(lg)}")
                }
            }
        }
    }

    private fun loadNotes() {
        val userFort = app.users.findOneFort(app.currentUser, placemark.id)
        val notes = userFort?.note

        if(notes != null) {
            showNotes(notes)
        }
    }

    private fun loadImage() {
        val userFort = app.users.findOneFort(app.currentUser, placemark.id)
        val img = userFort?.image_list

        if(img != null) {
            showNotes(img)
        }
    }

    private fun showNotes(notes: List<String>) {
        note_view.adapter = NotesAdapter(notes, this)
        note_view.adapter?.notifyDataSetChanged()
    }

    private fun showImages(img: List<String>) {
        image_view.adapter = NotesAdapter(img, this)
        image_view.adapter?.notifyDataSetChanged()
    }

    override fun delNote(index: Int) {
        placemark.note = placemark.note.filterIndexed { i, s -> i != index }
        note_view.adapter = NotesAdapter(placemark.note, this)
    }

    override fun delImg(index: Int) {
        placemark.image_list = placemark.image_list.filterIndexed { i, s -> i != index }
        image_view.adapter = ImageAdapter(placemark.image_list, this)
    }


    // Map methods
    public override fun onResume() {
        map.onResume()
        super.onResume()
    }

    public override fun onPause() {
        super.onPause()
        map.onPause()
    }

    public override fun onDestroy() {
        super.onDestroy()
        map.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map.onLowMemory()
    }

    // Code from: https://stackoverflow.com/questions/16536414/how-to-use-mapview-in-android-using-google-map-v2  //
    private fun setMapLocation(map: GoogleMap, location: LatLng) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 5f))
        with(map) {
            addMarker(
                MarkerOptions().position(
                    location
                )
            )
            mapType = GoogleMap.MAP_TYPE_NORMAL
        }
    }
}

