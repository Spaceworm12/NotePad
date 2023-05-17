package com.example.homework.presentation.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.homework.databinding.FragmentPreviewBinding
import com.example.homework.databinding.FragmentViewBinding
import com.example.homework.presentation.model.NoteModel


class ListAdapter(
    private val clickListener: (NoteModel) -> Unit,
    private val longClickListener: (Long) -> Unit
) :
    ListAdapter<NoteModel, ListHolder>(DIFF_CALLBACK) {


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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentViewBinding.inflate(inflater, parent, false)
        return ListHolder(binding)
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        val noteModel = getItem(position)
        holder.bind(noteModel)
        holder.binding.root.setOnClickListener { clickListener(noteModel) }
        holder.binding.root.setOnLongClickListener {
            longClickListener.invoke(noteModel.id)
            true
        }

    }
}


