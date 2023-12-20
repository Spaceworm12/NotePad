package com.example.homework.presentation.recycler

import com.example.homework.presentation.model.NoteModel


sealed class ListEvents {
    object GetNotes : ListEvents()
    class DeleteNote(val id: Long) : ListEvents()
    class DeleteNoteModel(val note: NoteModel) : ListEvents()
    object DeleteAll : ListEvents()
    class SaveUserDate(val note: NoteModel) : ListEvents()
    class ShowSettingsDialog(val itsShow: Boolean) : ListEvents()
    class ShowSettingsDialogRadio(val itsShow: Boolean) : ListEvents()
    class ShowCalendar(val itsShow: Boolean, val note:NoteModel) : ListEvents()
    class ShowDeleteDialog(val itsShow: Boolean, val id: Long) : ListEvents()
    class ShowChangeDialog(val itsShow: Boolean, val note:NoteModel) : ListEvents()
    class SaveCurrentNote(val note: NoteModel) : ListEvents()
    class ClearAll(val itsShow: Boolean) : ListEvents()
    class ChangeTheme(val themeCode: Int) : ListEvents()

}
