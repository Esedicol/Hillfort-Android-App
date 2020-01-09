package org.wit.placemark.views.placemark_list

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.info
import org.jetbrains.anko.uiThread
import org.wit.placemark.models.HillfortModel
import org.wit.placemark.views.base.BasePresenter
import org.wit.placemark.views.base.BaseView
import org.wit.placemark.views.base.VIEW

class HillfortListPresenter(view: BaseView) : BasePresenter(view) {

    private var currentHillforts: List<HillfortModel> = arrayListOf()

    fun doEditHillfort(hillfort: HillfortModel) {
        view?.navigateTo(VIEW.HILLFORT, 0, "hillfort_edit", hillfort)
    }

    fun loadHillforts() {
        doAsync {
            val hillforts = app.hillforts.findAllHillforts()
            view?.info(hillforts)
            uiThread {
                if (hillforts != null) {
                    currentHillforts = hillforts
                    view?.showHillforts(hillforts)
                }
            }
        }
    }

    fun doSortFavourite() {
        val favourites = app.hillforts.sortedByFavourite()
        if (favourites != null) {
            currentHillforts = favourites
            view?.showHillforts(favourites)
        }
    }

    fun doSearch(query: String) {
        val foundHillforts = app.hillforts.findHillfortsByName(query)
        view?.info(foundHillforts)
        if (foundHillforts != null) {
            currentHillforts = foundHillforts
            view?.showHillforts(foundHillforts)
        }
    }


}