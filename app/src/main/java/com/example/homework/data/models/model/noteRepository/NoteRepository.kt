package com.example.homework.data.models.model.noteRepository

import com.example.homework.data.models.model.noteModel.NoteModel

interface NoteRepository {
    fun getNotes(): List<NoteModel>
}