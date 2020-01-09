package org.wit.placemark.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.placemark_view.view.*
import org.wit.placemark.R
import org.wit.placemark.models.HillfortModel

interface HillfortListener {
    fun onHillfortClick(hillfort: HillfortModel)
}

class HillfortListAdapter constructor(
    private var hillforts: List<HillfortModel>, private val listener: HillfortListener
) : RecyclerView.Adapter<HillfortListAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.placemark_view,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val hillfort = hillforts[holder.adapterPosition]
        holder.bind(hillfort, listener)
    }

    override fun getItemCount(): Int = hillforts.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(hillfort: HillfortModel, listener: HillfortListener) {

            val location =
                "LAT: ${"%.4f".format(hillfort.location.lat)} | LNG: ${"%.4f".format(hillfort.location.lng)}"
            val isVisited = if (hillfort.visited) "Yes" else "No"

            itemView.hillfortRecycleItemName.text = hillfort.name
            itemView.hillfortRecycleItemLocation.text = location
            itemView.hillfortRecycleItemVisited.text = "Visited: $isVisited"
            itemView.hillfortRecycleItemRating.rating = hillfort.rating.toFloat()
            if (hillfort.isFavourite) {
                itemView.hillfortRecycleItemFavouriteIcon.setBackgroundResource(
                    R.drawable.ic_favorite_clicked
                )
            }

            if (hillfort.images.size != 0) {
                Glide.with(itemView.context).load(hillfort.images[0].uri).centerCrop()
                    .into(itemView.hillfortRecycleItemImageIcon)
            } else {
                itemView.hillfortRecycleItemImageIcon.setImageResource(R.drawable.placeholder)
            }
            itemView.setOnClickListener { listener.onHillfortClick(hillfort) }

        }
    }
}