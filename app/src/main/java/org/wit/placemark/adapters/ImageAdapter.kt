package org.wit.placemark.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_images.view.*
import kotlinx.android.synthetic.main.card_placemark.view.*
import org.wit.placemark.R
import org.wit.placemark.helpers.readImageFromPath

interface ImageListener {
    fun delImg(removeIndex: Int)
}

class ImageAdapter constructor(
    var images: List<String>,
    val listener: ImageListener
) :
    RecyclerView.Adapter<ImageAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_images,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val img = images[holder.adapterPosition]
        holder.bind(img, position, listener)
    }


    override fun getItemCount(): Int = images.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(imageString: String, position: Int, listener: ImageListener) {
            itemView.cardImage.setImageBitmap(readImageFromPath(itemView.context, imageString))
            itemView.delete_image.setOnClickListener { listener.delImg(position) }
        }

    }


}