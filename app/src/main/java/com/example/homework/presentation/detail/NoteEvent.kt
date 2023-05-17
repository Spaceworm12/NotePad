package com.example.homework.presentation.detail

sealed class NoteEvent {

    class SaveUserTitle(val text: String) : NoteEvent()

    class SaveUserDescription(val text: String) : NoteEvent()

    class SaveNote(val id: Long) : NoteEvent()

    class DeleteNote(val id: Long): NoteEvent()
}