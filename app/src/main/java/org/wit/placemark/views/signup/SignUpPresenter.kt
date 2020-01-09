package org.wit.placemark.views.signup

import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.toast
import org.wit.placemark.models.UserModel
import org.wit.placemark.views.base.BasePresenter
import org.wit.placemark.views.base.BaseView
import org.wit.placemark.views.base.VIEW

class SignUpPresenter(view: BaseView) : BasePresenter(view) {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun doSignup(user: UserModel) {
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener(view!!) { task ->
                if (task.isSuccessful) {
                    view?.navigateTo(VIEW.LOGIN)
                } else {
                    view?.toast("Sign Up Failed: ${task.exception?.message}")
                }
            }
    }
}