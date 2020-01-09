package org.wit.placemark.views.settings.fragments.stats

import org.wit.placemark.models.HillfortModel
import org.wit.placemark.views.settings.fragments.BaseFragmentPresenter

class StatsPresenter(private val fragment: Stats) :
    BaseFragmentPresenter(fragment) {

    var hillforts: List<HillfortModel> = app.hillforts.findAllHillforts()!!

    fun getAverageRatings(): Double {
        var average = 0.0
        if(hillforts.isEmpty()) {
            average = 0.0
        }
        else {
            hillforts.forEach {
                average += it.rating
            }
            average /= hillforts.size
        }
        return average
    }

    fun getAllFavourites(): Int {
        var favourites = 0
        hillforts.forEach {
            if (it.isFavourite) {
                favourites++
            }
        }
        return favourites
    }

    fun getAllVisited(): Int {
        var visited = 0
        hillforts.forEach {
            if (it.visited) {
                visited++
            }
        }
        return visited
    }

    fun getAllNotes(): Int {
        var notes = 0
        hillforts.forEach {
            notes += it.notes.size
        }
        return notes
    }

    fun getAllImages(): Int {
        var images = 0
        hillforts.forEach {
            images += it.images.size
        }
        return images
    }
}