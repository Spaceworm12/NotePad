package com.example.homework.presentation.recycler.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.homework.databinding.FragmentNoteBoxBinding
import com.example.homework.presentation.model.NoteModel


class NotesHolder(val binding: FragmentNoteBoxBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(noteModel: NoteModel) {
        binding.noteName.text = noteModel.name
        binding.bdDate.text = noteModel.date
        binding.noteId.text = noteModel.id.toString()

    }
}

