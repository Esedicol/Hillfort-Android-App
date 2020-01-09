package org.wit.placemark.views.settings.fragments.stats


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_settings.view.*
import org.jetbrains.anko.AnkoLogger
import org.wit.placemark.R
import org.wit.placemark.views.settings.fragments.BaseFragment

class Stats : BaseFragment(), AnkoLogger {

    companion object {
        fun newInstance() = Stats()
    }

    private lateinit var presenter: StatsPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.activity_settings, container, false)
        presenter = initPresenter(StatsPresenter(this)) as StatsPresenter

        // set stats
        view.s_total.text = presenter.hillforts.size.toString()
        view.s_img.text = presenter.getAllImages().toString()
        view.s_visited.text = presenter.getAllVisited().toString()
        view.s_notes.text = presenter.getAllNotes().toString()
        view.s_ratings.text = presenter.getAverageRatings().toString()
        view.s_fav.text = presenter.getAllFavourites().toString()

        return view
    }
}