package com.example.homework.presentation.recycler

sealed class NotesListEvent {
    object GetNotes : NotesListEvent()
    class DeleteNote(val id: Long) : NotesListEvent()

}
