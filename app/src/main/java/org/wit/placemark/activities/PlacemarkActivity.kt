package org.wit.placemark.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.main_layout.*
import kotlinx.android.synthetic.main.main_layout.description
import kotlinx.android.synthetic.main.main_layout.placemarkTitle
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.placemark.R
import org.wit.placemark.helpers.showImagePicker
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.Location
import org.wit.placemark.models.PlacemarkModel
import org.wit.placemark.models.Note
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class PlacemarkActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    var placemark = PlacemarkModel()
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    val NOTE_REQUEST = 3
    var location = Location()
    var date = String()
    var edit = false
    var dateFormat = SimpleDateFormat("dd MMM, YYYY", Locale.UK)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        app = application as MainApp
        // user = app.currentUser


        // ------------- Select Image Button  ------------- //
        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }


        // ------------- Select Location Button  ------------- //
        placemarkLocation.setOnClickListener {
            startActivityForResult(
                intentFor<MapActivity>().putExtra("location", location),
                LOCATION_REQUEST
            )
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

        // ------------- Add Note Button ------------- //
        addNote.setOnClickListener {
            val noteTitle = noteTitle.text.toString()
            val noteContent = noteContent.text.toString()

            if(noteTitle.isNotEmpty() && noteContent.isNotEmpty()) {
                val newNote = Note()

                newNote.id = generateRandomId().toInt()
                newNote.title = noteTitle
                newNote.content = noteContent

                app.users.createNote(app.currentUser, placemark, newNote)
            } else {

            }
        }

        // ------------- Edit Mode ------------- //
        if (intent.hasExtra("placemark_edit")) {
            edit = true
            toast("!! WARNING YOU ARE NOW IN EDIT MODE !!")

            placemark = intent.extras?.getParcelable("placemark_edit")!!

            placemarkTitle.setText(placemark.title)
            description.setText(placemark.description)
            location = placemark.location
            checkBox.isChecked = placemark.check_box
            dateText.text = placemark.date

            if (placemark.image_list.size == 0) {
                placemarkImage.setImageResource(R.drawable.default_image)
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
                toast("ADD")

                placemark.title = placemarkTitle.text.toString()
                placemark.description = description.text.toString()
                placemark.check_box = checkBox.isChecked
                placemark.date = date

                if (edit) {
                    toast("UPDATING ....")
                    app.users.updateFort(app.currentUser, placemark)
                } else {
                    try {
                        app.users.createFort(app.currentUser, placemark)
                        toast("NEW HILLFORT ADDED")
                    } catch (e: Exception) {
                        toast("FAILED TO ADD FORT")
                    }
                }
                setResult(Activity.RESULT_OK)
                finish()

                startActivity(Intent(this@PlacemarkActivity, PlacemarkListActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item!!)
    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when (requestCode) {
//            IMAGE_REQUEST -> {
//                if (data != null) {
//                    placemark.image_list += data.data.toString()
//                    placemarkImage.adapter = ImageAdapter(placemark.image_list, this)
//                }
//            }
//            LOCATION_REQUEST -> {
//                if (data != null) {
//                    location = data.extras?.getParcelable<Location>("location")!!
//                    lat.text = "%.6f".format(location.lat.toString())
//                    lng.text = "%.6f".format(location.lng.toString())
//                }
//            }
//        }
//    }

    fun generateRandomId(): Long {
        return Random().nextLong()
    }

    private fun loadNotes() {
        val note = app.currentUser.placemarks[placemark.id].note
        showNotes(note)
    }

    private fun showNotes(notes: ArrayList<Note>) {
        recyclerNotes.adapter = NotesAdapter(notes, this)
        recyclerNotes.adapter?.notifyDataSetChanged()
    }
}

