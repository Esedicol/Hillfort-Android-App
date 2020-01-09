package org.wit.placemark.views.base

import android.app.ActivityOptions
import android.content.Intent
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import org.jetbrains.anko.AnkoLogger
import org.wit.placemark.helpers.constructEmailTemplate
import org.wit.placemark.models.HillfortModel
import org.wit.placemark.models.ImageModel
import org.wit.placemark.models.NoteModel
import org.wit.placemark.views.editlocation.EditLocationView
import org.wit.placemark.views.placemark.HillfortView
import org.wit.placemark.views.placemark_list.HillfortListView
import org.wit.placemark.views.login.LoginView
import org.wit.placemark.views.main.MainView
import org.wit.placemark.views.map.HillfortMapsView
import org.wit.placemark.views.signup.SignUpView

val IMAGE_REQUEST = 1
val LOCATION_REQUEST = 2

enum class VIEW {
    LOCATION, HILLFORT, MAPS, LIST, SIGNUP, LOGIN
}

abstract class BaseView : MainView(), AnkoLogger {

    private var basePresenter: BasePresenter? = null

    fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
        val intent: Intent = when (view) {
            VIEW.LOGIN -> Intent(this, LoginView::class.java)
            VIEW.SIGNUP -> Intent(this, SignUpView::class.java)
            VIEW.LIST -> Intent(this, HillfortListView::class.java)
            VIEW.LOCATION -> Intent(this, EditLocationView::class.java)
            VIEW.HILLFORT -> Intent(this, HillfortView::class.java)
            VIEW.MAPS -> Intent(this, HillfortMapsView::class.java)
        }
        if (key != "") {
            intent.putExtra(key, value)
        }

        startActivityForResult(
            intent,
            code,
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
        )
    }

    fun initPresenter(presenter: BasePresenter): BasePresenter {
        basePresenter = presenter
        return presenter
    }

    fun createShareIntent(value: Parcelable?) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Love")
        val shareMessage = constructEmailTemplate(value as HillfortModel)
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, "Choose an Application"))
    }

    override fun onDestroy() {
        basePresenter?.onDestroy()
        super.onDestroy()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            basePresenter?.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    open fun showHillfort(hillfort: HillfortModel) {}
    open fun showHillforts(hillforts: List<HillfortModel>) {}
    open fun showNotes(notes: ArrayList<NoteModel>?) {}
    open fun showImages(images: ArrayList<ImageModel>) {}
    open fun showUpdatedMap(latLng: LatLng) {}
    open fun load() {}
    open fun hideProgress() {}

}