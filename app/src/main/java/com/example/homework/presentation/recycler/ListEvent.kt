package com.example.homework.presentation.recycler


sealed class ListEvent {
    object GetNotes : ListEvent()
    class DeleteNote(val id: Long) : ListEvent()
    class DeleteAll : ListEvent()

}
