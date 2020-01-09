package org.wit.placemark.views.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.placemark.R
import org.wit.placemark.views.base.BaseView
import org.wit.placemark.views.base.VIEW

class LoginView : BaseView(), AnkoLogger {

    private lateinit var presenter: LoginPresenter

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var signupButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = initPresenter(LoginPresenter(this)) as LoginPresenter
        email = findViewById(R.id.loginEmailInput)
        password = findViewById(R.id.loginPasswordInput)
        signupButton = findViewById(R.id.loginSignUpButton)
        loginButton = findViewById(R.id.loginButton)

        progressBar.visibility = View.GONE

        signupButton.setOnClickListener {
            navigateTo(VIEW.SIGNUP)
            email.text.clear()
            password.text.clear()
        }

        loginButton.setOnClickListener {
            login()
        }
    }

    private fun login() {

        val usernameText = email.text.toString()
        val passwordText = password.text.toString()

        if (usernameText.isEmpty() || passwordText.isEmpty()) {
            toast("Error empty inputs")
        } else {
            presenter.doLogin(usernameText, passwordText)
        }
    }

    override fun load() {
        progressBar.visibility = View.VISIBLE
    }

}