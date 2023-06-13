package com.example.homework.presentation.detail

import com.example.homework.presentation.detailBd.BdEvent

sealed class NoteEvent {

    class SaveUserTitle(val text: String) : NoteEvent()

    class SaveUserDescription(val text: String) : NoteEvent()

    class SaveNote(val id: Long) : NoteEvent()

    class SaveDateBd(val text: String) : NoteEvent()

    class DeleteNote(val id: Long) : NoteEvent()

    object Exit : NoteEvent()

    object Error : NoteEvent()
}