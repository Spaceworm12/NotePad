package com.example.homework.presentation.recycler

import com.example.homework.presentation.model.NoteModel

sealed class NotesListEvent {
    object GetNotes : NotesListEvent()

    class AddNote(val note: NoteModel) : NotesListEvent()

    class DeleteNote(val note: NoteModel, val index: Int) : NotesListEvent()
}
