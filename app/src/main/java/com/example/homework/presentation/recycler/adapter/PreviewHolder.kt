package com.example.homework.presentation.recycler.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.homework.databinding.FragmentDescriptionBinding


class PreviewHolder(val binding: FragmentDescriptionBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(noteModel: com.example.homework.presentation.model.NoteModel) {
        binding.noteName.text = noteModel.name
        binding.noteId.text = noteModel.id.toString()

    }


}