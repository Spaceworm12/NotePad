package com.example.homework.presentation.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.homework.databinding.FragmentBdBoxBinding
import com.example.homework.databinding.FragmentNoteBoxBinding
import com.example.homework.presentation.model.NoteModel


class NotesAdapter(
    private val clickListener: (NoteModel) -> Unit,
    private val longClickListener: (Long) -> Unit
) :
    ListAdapter<NoteModel, ViewHolder>(DIFF_CALLBACK) {


    companion object {


        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NoteModel>() {
            override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (getItem(itemCount - 1).type == "NOTE_TYPE") {
            val inflater = LayoutInflater.from(parent.context)
            val binding = FragmentNoteBoxBinding.inflate(inflater, parent, false)
            NotesHolder(binding)
        } else {
            val inflater = LayoutInflater.from(parent.context)
            val binding = FragmentBdBoxBinding.inflate(inflater, parent, false)
            BdHolder(binding)
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val noteModel = getItem(position).type
        if (getItem(itemCount - 1).type == "NOTE_TYPE") {
        holder.bin
        holder.binding.root.setOnClickListener { clickListener(noteModel) }
        holder.binding.root.setOnLongClickListener {
            longClickListener.invoke(noteModel.id)
            true
        }
    }
}






