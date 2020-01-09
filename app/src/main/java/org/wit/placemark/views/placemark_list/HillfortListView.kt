package org.wit.placemark.views.placemark_list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_lists.*
import kotlinx.android.synthetic.main.main_template.*
import org.wit.placemark.R
import org.wit.placemark.models.HillfortModel
import org.wit.placemark.views.adapters.HillfortListAdapter
import org.wit.placemark.views.adapters.HillfortListener
import org.wit.placemark.views.base.BaseView
import org.wit.placemark.views.base.VIEW


class HillfortListView : BaseView(),
    HillfortListener {

    private lateinit var presenter: HillfortListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_lists, content_frame)

        presenter = initPresenter(HillfortListPresenter(this)) as HillfortListPresenter

        val layoutManager = LinearLayoutManager(this)
        hillfort_list.layoutManager = layoutManager
        presenter.loadHillforts()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort_list, menu)
        toolbar.overflowIcon = getDrawable(R.drawable.ic_filter)

        val item = menu?.findItem(R.id.search_hilforts)
        val searchView: SearchView = item?.actionView as SearchView

        searchView.isSubmitButtonEnabled = true
        searchView.queryHint = "Search Hillfort ..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(currentText: String): Boolean {
                presenter.doSearch(currentText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                presenter.doSearch(query)
                return true
            }
        })

        searchView.setOnCloseListener {
            presenter.loadHillforts()
            true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun showHillforts(hillforts: List<HillfortModel>) {
        val context = hillfort_list.context
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_fall_down)

        hillfort_list.layoutAnimation = controller

        hillfort_list.adapter = HillfortListAdapter(hillforts, this)
        hillfort_list.adapter?.notifyDataSetChanged()
        hillfort_list.scheduleLayoutAnimation()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_hillfort -> {
                navigateTo(VIEW.HILLFORT)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onHillfortClick(hillfort: HillfortModel) {
        presenter.doEditHillfort(hillfort)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.loadHillforts()
        super.onActivityResult(requestCode, resultCode, data)
    }
}

