package com.example.notepad

import android.content.ClipData.Item
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.homework.Note
import com.example.homework.databinding.FragmentDescriptionBinding


class Holder(val binding: FragmentDescriptionBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(noteModel: Note) {
        binding.noteName.text = noteModel.name
        binding.noteId.text = noteModel.id.toString()

    }


}