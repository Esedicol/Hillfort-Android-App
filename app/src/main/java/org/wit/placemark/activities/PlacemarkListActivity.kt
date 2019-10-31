package org.wit.placemark.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_placemark_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.placemark.Auth.LoginActivity
import org.wit.placemark.Auth.RegisterActivity
import org.wit.placemark.Auth.UserSettings
import org.wit.placemark.R
import org.wit.placemark.adapters.PlacemarkAdapter
import org.wit.placemark.adapters.PlacemarkListener
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.PlacemarkModel
import org.wit.placemark.models.UserModel

class PlacemarkListActivity : AppCompatActivity(), PlacemarkListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placemark_list)

        app = application as MainApp

        toolbar.title = title
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = PlacemarkAdapter(app.currentUser.placemarks, this)
        loadPlacemarks()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPlacemarkClick(placemark: PlacemarkModel) {
        startActivityForResult(
            intentFor<PlacemarkActivity>().putExtra("placemark_edit", placemark),
            0
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadPlacemarks()
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<PlacemarkActivity>(0)
            R.id.item_stats -> startActivity(intentFor<StatsActivity>())
            R.id.item_settings -> startActivity(intentFor<UserSettings>())
            R.id.item_logout -> {
                toast("Logged Out")
                logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadPlacemarks() {
        showPlacemarks(app.currentUser.placemarks)
    }

    fun showPlacemarks(placemarks: List<PlacemarkModel>) {
        recyclerView.adapter = PlacemarkAdapter(placemarks, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun del(placemark: PlacemarkModel) {
        app.users.deleteFort(app.currentUser, placemark)
        loadPlacemarks()
        toast("YAY! Fort Successfully Deleted")
    }

    fun logout() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}