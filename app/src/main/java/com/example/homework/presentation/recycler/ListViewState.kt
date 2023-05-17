package com.example.homework.presentation.recycler

import com.example.homework.presentation.model.NoteModel

data class ListViewState(
    val notesList: List<NoteModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorText: String = ""
) {
    fun getEmptyItem(): NoteModel {
        return NoteModel(
            id = 0,
            name = "",
            description = ""
        )
    }
}
