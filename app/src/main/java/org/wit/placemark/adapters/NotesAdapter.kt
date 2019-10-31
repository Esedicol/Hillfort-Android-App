package org.wit.placemark.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_notes.view.*
import org.wit.placemark.R

interface NoteListener {
    fun del(removeIndex: Int)
}

class NotesAdapter constructor(
    private var notes: List<String>,
    private val listener: NoteListener
) :
    RecyclerView.Adapter<NotesAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_notes,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val notes = notes[holder.adapterPosition]
        holder.bind(notes, position, listener)
    }

    override fun getItemCount(): Int = notes.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(note: String, position: Int, listener: NoteListener) {
            itemView.note_content.text = note
            itemView.setOnClickListener{ listener.del(position) }
        }
    }


}