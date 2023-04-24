package com.example.homework

import androidx.recyclerview.widget.RecyclerView
import com.example.homework.databinding.FragmentDescriptionBinding


class PreviewHolder(val binding: FragmentDescriptionBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(noteModel: NoteModel) {
        binding.noteName.text = noteModel.name
        binding.noteId.text = noteModel.id.toString()

    }


}