package org.wit.placemark.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_layout.toolbarAdd
import kotlinx.android.synthetic.main.stats_page.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.placemark.Auth.LoginActivity
import org.wit.placemark.Auth.UserSettings
import org.wit.placemark.R
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.PlacemarkModel


class StatsActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    var placemark = PlacemarkModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stats_page)

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        app = application as MainApp

        noForts.text = nForts().toString()
        noVisits.text = nVisits().toString()
        noImages.text = nImages().toString()
        noNotes.text = nNotes().toString()
    }

    private fun nForts(): Int {
        return app.users.findAllForts(app.currentUser).size
    }

    private fun nVisits(): Int {
        var total = 0
        val forts: List<PlacemarkModel> = app.users.findAllForts(app.currentUser)

        for (v in forts) {
            if (v.check_box) {
                total++
            }
        }
        return total
    }

    private fun nImages(): Int {
        var total = 0
        val forts: List<PlacemarkModel> = app.users.findAllForts(app.currentUser)

        for (i in forts) {
            total = i.image_list.size
        }
        return total
    }


    private fun nNotes(): Int {
        var total = 0
        val forts: List<PlacemarkModel> = app.users.findAllForts(app.currentUser)

        for (i in forts) {
            total = i.note.size
        }
        return total
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
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

    fun logout() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}


