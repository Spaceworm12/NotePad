package com.example.homework.presentation.recycler


sealed class NotesEvents {
    object GetNotes : NotesEvents()
    class DeleteNote(val id: Long) : NotesEvents()
    class DeleteAll : NotesEvents()
    class Update(val id: Long, val date: String) : NotesEvents()


}
