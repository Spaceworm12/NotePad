package com.example.homework.presentation.recycler


sealed class ListEvents {
    object GetNotes : ListEvents()
    class DeleteNote(val id: Long) : ListEvents()
    class DeleteAll : ListEvents()
    class SaveUserDate(val date: String, val id: Long) : ListEvents()

}
