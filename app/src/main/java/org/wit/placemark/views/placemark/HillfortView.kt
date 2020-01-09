package org.wit.placemark.views.placemark

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_placemark.*
import kotlinx.android.synthetic.main.main_template.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.placemark.R
import org.wit.placemark.models.HillfortModel
import org.wit.placemark.models.ImageModel
import org.wit.placemark.models.Location
import org.wit.placemark.models.NoteModel
import org.wit.placemark.views.adapters.HillfortImageAdapter
import org.wit.placemark.views.adapters.HillfortNotesAdapter
import org.wit.placemark.views.adapters.NoteListener
import org.wit.placemark.views.base.BaseView

class HillfortView : BaseView(),
    NoteListener, AnkoLogger {

    private lateinit var presenter: HillfortPresenter
    private var location = Location()
    private var isFabOpen = false

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_placemark, content_frame)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        presenter = initPresenter(HillfortPresenter(this)) as HillfortPresenter

        with(map_view) {
            onCreate(savedInstanceState)
            getMapAsync {
                presenter.doConfigureMap(it)
                it.setOnMapClickListener { presenter.doSetLocation() }
            }
        }

        add_img.setOnClickListener {
            presenter.doSelectImage()
        }

        p_fav.setOnClickListener {
            presenter.doFavourite()
        }

        p_visited.setOnClickListener {
            showDatePickerDialog()
        }

        add_note.setOnClickListener {
            if (note_content.text.toString() == "") {
                toast("ERROR: Empty description")
            } else {
                presenter.doAddNote(note_content.text.toString())
            }
        }
//
//        favourite_btn.setOnClickListener {
//            if(favourite_btn.equals(R.drawable.ic_unfavourite)) {
//                favourite_btn.setImageResource(R.drawable.ic_favorite_clicked)
//                presenter.doFavourite()
//            } else if(favourite_btn.equals(R.drawable.ic_favorite_clicked)) {
//                favourite_btn.setImageResource(R.drawable.ic_unfavourite)
//            }
//        }


        p_save.setOnClickListener {
            if (note_content.text.toString() == "") {
                toast("ERROR: Empty fields")
            } else {
                val newFort = HillfortModel()

                newFort.name = p_title.text.toString().trim()
                newFort.description = p_description.text.toString().trim()
                newFort.visited = p_visited.isChecked
                newFort.dateVisited = p_dateVisited.text.toString().trim()
                newFort.location = location
                newFort.rating = p_rating.rating.toInt()

                presenter.doSave(newFort)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_return, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.p_back -> {
                finish()
            }

            R.id.sortByFavourite -> {
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNoteClick(noteModel: NoteModel) {

    }

    override fun showHillfort(hillfort: HillfortModel) {
        p_title.setText(hillfort.name)
        p_description.setText(hillfort.description)
        p_visited.isChecked = hillfort.visited
        p_dateVisited.setText(hillfort.dateVisited)
        p_rating.rating = hillfort.rating.toFloat()

        showNotes(hillfort.notes)
        showImages(hillfort.images)
    }

    // Credit: https://tutorial.eyehunts.com/android/android-date-picker-dialog-example-kotlin/
    @TargetApi(Build.VERSION_CODES.N)
    private fun showDatePickerDialog() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                p_dateVisited.setText("$dayOfMonth/${monthOfYear + 1}/$year")
            },
            year, month, day
        )
        dpd.show()
    }

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

    override fun showNotes(notes: ArrayList<NoteModel>?) {
        val layoutManager = LinearLayoutManager(this)
        val recyclerNotes = findViewById<RecyclerView>(R.id.note_view)
        recyclerNotes.layoutManager = layoutManager
        if (notes != null) {
            recyclerNotes.adapter =
                HillfortNotesAdapter(notes, this)
            recyclerNotes.adapter?.notifyDataSetChanged()


        }
    }

    override fun showImages(images: ArrayList<ImageModel>) {
        val imageViewPager = findViewById<ViewPager>(R.id.p_img)
        if (images != null) {
            imageViewPager.adapter =
                HillfortImageAdapter(
                    images,
                    this
                )
            imageViewPager.adapter?.notifyDataSetChanged()
        }
    }

    override fun showUpdatedMap(latLng: LatLng) {

        lat.text = "LAT: ${"%.4f".format(latLng.latitude)}"
        lng.text = "LNG: ${"%.4f".format(latLng.longitude)}"

        map_view.getMapAsync { it.clear() }
        map_view.getMapAsync {
            setMapLocation(it, latLng)
        }
    }
}