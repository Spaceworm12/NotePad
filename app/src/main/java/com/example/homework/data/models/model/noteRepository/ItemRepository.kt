package com.example.homework.data.models.model.noteRepository

import com.example.homework.data.models.model.noteModel.NoteModel

interface ItemRepository {
    fun getItems(): List<NoteModel>
}