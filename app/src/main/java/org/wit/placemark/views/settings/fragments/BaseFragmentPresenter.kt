package org.wit.placemark.views.settings.fragments

import org.wit.placemark.MainApp

open class BaseFragmentPresenter(fragment: BaseFragment) {
    open var app: MainApp = fragment.activity?.application as MainApp
}