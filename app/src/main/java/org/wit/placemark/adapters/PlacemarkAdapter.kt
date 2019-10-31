package org.wit.placemark.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_placemark.view.*
import org.wit.placemark.R
import org.wit.placemark.helpers.readImageFromPath
import org.wit.placemark.models.PlacemarkModel
import java.text.DecimalFormat

interface PlacemarkListener {
    fun onPlacemarkClick(placemark: PlacemarkModel)
    fun del(placemark: PlacemarkModel)
}

class PlacemarkAdapter constructor(
    private var placemarks: List<PlacemarkModel>,
    private val listener: PlacemarkListener
) : RecyclerView.Adapter<PlacemarkAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_placemark,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val placemark = placemarks[holder.adapterPosition]
        holder.bind(placemark, listener)
    }

    override fun getItemCount(): Int = placemarks.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(placemark: PlacemarkModel, listener: PlacemarkListener) {
            itemView.cardTitle.text = placemark.title
            itemView.cardDescription.text = placemark.description
            itemView.cardCheck.text = "Visited: ${placemark.check_box}"

            val lt = "LAT: ${DecimalFormat("#.##").format(placemark.location.lat)}"
            val lg = "LAT: ${DecimalFormat("#.##").format(placemark.location.lng)}"
            itemView.cardLocation.text = "${lt} | ${lg}"

            itemView.setOnClickListener { listener.onPlacemarkClick(placemark) }
            itemView.delete_placemark.setOnClickListener { listener.del(placemark) }

            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, placemark.image_list[0]))
        }
    }
}