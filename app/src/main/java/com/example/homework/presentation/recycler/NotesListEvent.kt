package com.example.homework.presentation.recycler

import com.example.homework.presentation.model.NoteModel

sealed class NotesListEvent {
    object GetNotes : NotesListEvent()
    class DeleteNote(val id: Long) : NotesListEvent()

    class DeleteAll(): NotesListEvent()

}
