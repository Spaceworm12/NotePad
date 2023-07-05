package com.example.homework.presentation.detailbd

import com.example.homework.presentation.model.NoteModel

sealed class BirthdayEvent {
    class SetBirthdayNote(val note: NoteModel) : BirthdayEvent()
    class SaveBirthdayNote(val id: Long) : BirthdayEvent()

}