package org.wit.placemark.views.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_template.*
import org.jetbrains.anko.AnkoLogger
import org.wit.placemark.MainApp
import org.wit.placemark.R
import org.wit.placemark.views.placemark.HillfortView
import org.wit.placemark.views.placemark_list.HillfortListView
import org.wit.placemark.views.login.LoginView
import org.wit.placemark.views.map.HillfortMapsView
import org.wit.placemark.views.settings.ExtraView

open class MainView : AppCompatActivity(), AnkoLogger {

    lateinit var bottomNavBar: BottomNavigationView
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_template)
        layoutInflater.inflate(R.layout.activity_lists, content_frame)

        app = application as MainApp

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        bottomNavBar = findViewById(R.id.menu_footer)
        bottomNavBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.footer_list -> {
                    startActivity(
                        Intent(this, HillfortListView::class.java)
                    )
                }
                R.id.footer_add -> {
                    startActivity(
                        Intent(this, HillfortView::class.java)
                    )
                }
                R.id.footer_map -> {
                    startActivity(
                        Intent(this, HillfortMapsView::class.java)
                    )
                }

                R.id.footer_settings -> {
                    startActivity(
                        Intent(this, ExtraView::class.java)
                    )
                }
                R.id.footer_logout -> {
                    app.hillforts.logout()
                    app.activeUser = null
                    startActivity(
                        Intent(this, LoginView::class.java)
                    )
                }
            }
            true
        }

    open fun setNavigationBarItem() {}

}