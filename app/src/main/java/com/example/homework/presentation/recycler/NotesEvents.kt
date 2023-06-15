package com.example.homework.presentation.recycler


sealed class NotesEvents {
    object GetNotes : NotesEvents()
    class DeleteNote(val id: Long) : NotesEvents()
    class DeleteAll : NotesEvents()
    class SaveUserDate(val date: String, val id: Long) : NotesEvents()


}
