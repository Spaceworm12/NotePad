package com.example.homework.presentation.detailbd

import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.recycler.ListEvents

sealed class BirthdayEvent {
    class ShowCalendar(val itsShow: Boolean) : BirthdayEvent()
    class SetBirthdayNote(val note: NoteModel) : BirthdayEvent()
    class SaveBirthdayNote(val id: Long) : BirthdayEvent()

}