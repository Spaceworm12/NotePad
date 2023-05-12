package com.example.homework.presentation.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.homework.databinding.FragmentDescriptionBinding
import com.example.homework.presentation.model.NoteModel


class NotesListAdapter(
    private val clickListener: (NoteModel) -> Unit,
    private val longClickListener: (Long) -> Unit
) :
    ListAdapter<NoteModel, NotesListHolder>(DIFF_CALLBACK) {


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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentDescriptionBinding.inflate(inflater, parent, false)
        return NotesListHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesListHolder, position: Int) {
        val noteModel = getItem(position)
        holder.bind(noteModel)
        holder.binding.root.setOnClickListener { clickListener(noteModel) }
        holder.binding.root.setOnLongClickListener {
            longClickListener.invoke(noteModel.id)
            true
        }

    }
}



