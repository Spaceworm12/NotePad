package com.example.homework.presentation.recycler

import com.example.homework.presentation.model.NoteModel


sealed class ListEvents {
    object GetNotes : ListEvents()
    class DeleteNote(val id: Long) : ListEvents()
    class DeleteNoteModel(val note: NoteModel) : ListEvents()
    object DeleteAll : ListEvents()
    class SaveUserDate(val date: String, val id: Long) : ListEvents()
    class ShowSettingsDialog(val itsShow: Boolean) : ListEvents()
    class ShowDateAddDialog(val itsShow: Boolean) : ListEvents()
    class ShowDeleteDialog(val itsShow: Boolean, val id: Long) : ListEvents()
    class ShowChangeDialog(val itsShow: Boolean) : ListEvents()
    class SaveCurrentNote(val note: NoteModel) : ListEvents()
    class ClearAll(val itsShow: Boolean) : ListEvents()
    class ChangeTheme(val themeCode: Int) : ListEvents()

}
