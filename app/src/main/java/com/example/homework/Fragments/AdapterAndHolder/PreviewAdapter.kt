package com.example.homework.Fragments.AdapterAndHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.homework.Model.NoteModel
import com.example.homework.databinding.FragmentDescriptionBinding


class PreviewAdapter(
    val clickListener: (NoteModel) -> Unit,
    val longClickListener: (Int, NoteModel) -> Unit
) :
    ListAdapter<NoteModel, PreviewHolder>(DIFF_CALLBACK) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentDescriptionBinding.inflate(inflater, parent, false)
        return PreviewHolder(binding)
    }

    override fun onBindViewHolder(holder: PreviewHolder, position: Int) {
        val noteModel = getItem(position)
        holder.bind(noteModel)
        holder.binding.root.setOnClickListener {
            clickListener(noteModel)
        }
        holder.binding.root.setOnLongClickListener {
            longClickListener.invoke(currentList.indexOf(noteModel), noteModel)
            true
        }

    }
}



