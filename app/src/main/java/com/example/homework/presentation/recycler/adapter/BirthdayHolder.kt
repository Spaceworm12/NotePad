package com.example.homework.presentation.recycler.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.homework.databinding.FragmentBirthdayBoxBinding
import com.example.homework.presentation.model.NoteModel


class BirthdayHolder(val binding: FragmentBirthdayBoxBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(noteModel: NoteModel) {
        binding.username.text = noteModel.name
        binding.bdDate.text = noteModel.date
        binding.bdId.text = noteModel.id.toString()
    }
}

