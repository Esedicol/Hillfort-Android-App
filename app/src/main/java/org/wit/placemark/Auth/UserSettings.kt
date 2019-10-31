package org.wit.placemark.Auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_layout.toolbarAdd
import kotlinx.android.synthetic.main.settings_page.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.placemark.R
import org.wit.placemark.activities.PlacemarkListActivity
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.PlacemarkModel
import org.wit.placemark.models.UserModel

class UserSettings : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    var placemark = PlacemarkModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_page)

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        app = application as MainApp

        fullName.setText(app.currentUser.name)
        new_email.setHint(app.currentUser.email)

        update.setOnClickListener {
            val newUser = UserModel()

            if (new_email.text.isEmpty() && new_pass.text.isEmpty()) {
                toast("Update Failed (Empty Inputs)")
            } else {
                if (old_pass.text.toString() != app.currentUser.password) {
                    toast("Old Password does not match")
                    reset()
                } else if(new_email.text.isEmpty()) {
                    newUser.id = app.currentUser.id
                    newUser.name = app.currentUser.name
                    newUser.email = app.currentUser.email
                    newUser.password = new_pass.text.toString()

                    app.users.updateUser(newUser)
                    toast("Update Complete")
                    reset()

                } else {
                    newUser.id = app.currentUser.id
                    newUser.name = app.currentUser.name
                    newUser.email = new_email.text.toString()
                    newUser.password = new_pass.text.toString()

                    app.users.updateUser(newUser)
                    toast("Update Complete")
                    reset()

                }
            }
        }

        back.setOnClickListener {
            startActivity(Intent(this@UserSettings, PlacemarkListActivity::class.java))
            finish()
        }
    }

    private fun reset() {
        new_email.setHint(app.currentUser.email)
        new_email.setText("")
        old_pass.setText("")
        new_pass.setText("")
    }

}