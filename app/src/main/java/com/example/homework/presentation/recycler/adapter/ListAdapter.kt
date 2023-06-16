package com.example.homework.presentation.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.homework.databinding.FragmentBirthdayBoxBinding
import com.example.homework.databinding.FragmentNoteBoxBinding
import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.model.NoteType

class ListAdapter(
    private val clickListener: (NoteModel) -> Unit,
    private val longClickListener: (Long) -> Unit
) :
    ListAdapter<NoteModel, ViewHolder>(DIFF_CALLBACK) {

    companion object {
        const val NOTE_TYPE = 11
        const val BIRTHDAY_TYPE = 15

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
        val inflater = LayoutInflater.from(parent.context)

        return checkHolderType(viewType, inflater, parent)
    }

    private fun checkHolderType(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ViewHolder {
        return returnViewType(viewType, inflater, parent)
    }

    private fun returnViewType(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ViewHolder {
        return when (viewType) {
            NOTE_TYPE -> {
                val binding = FragmentNoteBoxBinding.inflate(inflater, parent, false)
                ListHolder(binding)
            }

            else -> {
                val binding = FragmentBirthdayBoxBinding.inflate(inflater, parent, false)
                BirthdayHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val noteModel: NoteModel = getItem(position)
        when (holder) {
            is ListHolder -> {
                holder.bind(noteModel)
                holder.binding.root.setOnClickListener {
                    clickListener(noteModel)
                }
                holder.binding.root.setOnLongClickListener {
                    longClickListener.invoke(noteModel.id)
                    true
                }
            }

            is BirthdayHolder -> {
                holder.bind(noteModel)
                holder.binding.root.setOnClickListener {
                    clickListener(noteModel)
                }
                holder.binding.root.setOnLongClickListener {
                    longClickListener.invoke(noteModel.id)
                    true
                }
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        val note = getItem(position)
        return when (note.type) {
            NoteType.NOTE_TYPE -> NOTE_TYPE
            else -> BIRTHDAY_TYPE
        }
    }
}


