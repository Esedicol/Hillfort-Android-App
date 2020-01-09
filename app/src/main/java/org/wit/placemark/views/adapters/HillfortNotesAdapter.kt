package org.wit.placemark.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.notes_view.view.*
import org.wit.placemark.R
import org.wit.placemark.models.NoteModel

interface NoteListener {
    fun  onNoteClick(noteModel: NoteModel)
//    fun del(noteModel: RecyclerView)
}

class HillfortNotesAdapter constructor(private var notes: ArrayList<NoteModel>,
                                       private val listener: NoteListener
) : RecyclerView.Adapter<HillfortNotesAdapter.MainHolder>() {

//
//    fun removeAt(viewHolder: RecyclerView.ViewHolder) {
//        notes.removeAt(viewHolder.adapterPosition)
//        notifyItemRemoved(viewHolder.adapterPosition)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.notes_view,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val note = notes[holder.adapterPosition]
        holder.bind(note, listener)
    }

    override fun getItemCount(): Int = notes.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(noteModel: NoteModel, listener: NoteListener) {

            if (noteModel.content.length > 30) {
                // only show part of string to prevent recycleview from resizing
                itemView.noteContent.text = "${noteModel.content.substring(0, 30)}..."
            } else {
                itemView.noteContent.text = noteModel.content
            }

            itemView.setOnClickListener { listener.onNoteClick(noteModel) }
//            itemView.note_btn.setOnClickListener { listener.del() }
        }
    }
}