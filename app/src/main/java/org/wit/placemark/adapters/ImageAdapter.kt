package org.wit.placemark.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import org.jetbrains.anko.AnkoLogger
import org.wit.placemark.helpers.readImageFromPath

class ImageAdapter (
    private val images: List<String>,
    private val context: Context
):

    PagerAdapter(), AnkoLogger {

    override fun isViewFromObject(view: View, image: Any): Boolean {
        return view === image
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.FIT_CENTER
        imageView.setImageBitmap(readImageFromPath(context, images[position]))
        view.addView(imageView,0)
        return imageView
    }

    override fun getCount(): Int = images.size

    override fun destroyItem(container: ViewGroup, position: Int, image: Any) {
        container.removeView(image as ImageView)
    }

}