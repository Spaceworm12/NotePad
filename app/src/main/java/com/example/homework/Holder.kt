package com.example.notepad

import android.view.View
import android.view.View.OnLongClickListener
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.homework.Note
import com.example.homework.databinding.FragmentDescriptionBinding


class Holder(val binding: FragmentDescriptionBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(noteModel: Note) {
        binding.noteName.text = noteModel.name
        binding.noteId.text = noteModel.id.toString()

    }


}