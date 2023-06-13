package com.example.homework.presentation.recycler.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.homework.databinding.FragmentBdBoxBinding
import com.example.homework.presentation.model.NoteModel


class BdHolder(val binding: FragmentBdBoxBinding) :
    RecyclerView.ViewHolder(binding.root) {

     fun bind (noteModel: NoteModel) {
        binding.username.text = noteModel.name
        binding.bdDate.text = noteModel.date
         binding.bdId.text = noteModel.id.toString()
    }
}

