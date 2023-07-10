package com.example.homework.presentation.recycler


sealed class ListEvents {
    object GetNotes : ListEvents()
    class DeleteNote(val id: Long) : ListEvents()
    object DeleteAll : ListEvents()
    class SaveUserDate(val date: String, val id: Long) : ListEvents()
    class ShowSettingsDialog(val itsShow: Boolean) : ListEvents()
    class ShowDeleteDialog(val itsShow: Boolean, val id: Long) : ListEvents()
    class ClearAll(val itsShow: Boolean) : ListEvents()
    class ChangeTheme(val themeCode: Int) : ListEvents()

}
