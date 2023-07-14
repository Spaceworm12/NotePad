package com.example.homework.presentation.recycler

import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.model.NoteType

data class ListViewState(
    val notesList: List<NoteModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorText: String = "",
    val isShowDeleteDialog: Boolean = false,
    val isShowSettingsDialog: Boolean = false,
    val isShowChangeDialog: Boolean = false,
    val isShowDeleteAllDialog: Boolean = false,
    val currentTheme: Int = 0,
    val deletableNoteId: Long = -1L,
    val currentNote: NoteModel? = null,
    val isShowDateAddDialog: Boolean = false,

    ) {

    fun getEmptyNote(): NoteModel {
        return NoteModel(
            id = 0,
            name = "",
            description = "",
            type = NoteType.NOTE_TYPE,
            date = ""
        )
    }

    fun getEmptyBirth(): NoteModel {
        return NoteModel(
            id = 0,
            name = "",
            description = "",
            type = NoteType.BIRTHDAY_TYPE,
            date = ""
        )
    }

}
