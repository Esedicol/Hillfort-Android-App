package org.wit.placemark.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_layout.*
import org.jetbrains.anko.AnkoLogger
import org.wit.placemark.R
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.PlacemarkModel


class StatsActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    var placemark = PlacemarkModel()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        app = application as MainApp


    }

    fun nForts() : Int {
        return  app.users.findAllForts(app.currentUser).size
    }


}