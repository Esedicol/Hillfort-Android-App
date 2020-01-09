package org.wit.placemark.views.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_extra.*
import kotlinx.android.synthetic.main.main_template.*
import org.wit.placemark.R
import org.wit.placemark.views.base.BaseView
import org.wit.placemark.views.settings.fragments.stats.Stats

class ExtraView : BaseView() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        content_frame.removeAllViews()
        layoutInflater.inflate(R.layout.activity_extra, content_frame)

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> {
                        Stats.newInstance()
                    }
                    else -> {
                        Stats.newInstance()
                    }
                }
            }
            override fun getItemCount(): Int {
                return 3
            }
        }
    }
}



