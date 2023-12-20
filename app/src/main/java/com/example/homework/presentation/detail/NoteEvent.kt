package com.example.homework.presentation.detail

import com.example.homework.presentation.model.NoteModel


sealed class NoteEvent {
    class SetNote(val note: NoteModel) : NoteEvent()
    class SaveNote(val id: Long) : NoteEvent()
}