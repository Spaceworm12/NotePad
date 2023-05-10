package com.example.homework.presentation.recycler

import com.example.homework.presentation.model.NoteModel

data class NotesListViewState(
    val notesList: List<NoteModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorText: String = "error") {
    fun getEmptyItem(): NoteModel {
        return NoteModel(
            // если id равен 0, room db поймет, что такого элемента еще нет и автоматически присудит id
            id = 0,
            name = "",
            description = ""
        )
    }
}
