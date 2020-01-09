package org.wit.placemark.views.login

import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.placemark.models.firebase.HillfortFireStore
import org.wit.placemark.views.base.BasePresenter
import org.wit.placemark.views.base.BaseView
import org.wit.placemark.views.base.VIEW

class LoginPresenter(view: BaseView) : BasePresenter(view) {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var fireStore: HillfortFireStore? = null

    init {
        if (app.hillforts is HillfortFireStore) {
            fireStore = app.hillforts as HillfortFireStore
        }
    }

    fun doLogin(email: String, password: String) {
        view?.load()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                app.activeUser = auth.currentUser!!
                view?.info(fireStore)
                if (fireStore != null) {
                    doAsync {
                        fireStore!!.fetchHillforts {
                            fireStore!!.fetchFavourites()
                            view?.hideProgress()
                            view?.navigateTo(VIEW.LIST, 0, "user", auth.currentUser)
                        }
                    }
                } else {
                    view?.hideProgress()
                    view?.navigateTo(VIEW.LIST)
                }
            } else {
                view?.hideProgress()
                view?.toast("Sign Up Failed: ${task.exception?.message}")
            }
        }
    }
}