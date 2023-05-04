package com.example.homework.presentation.recycler

import com.example.homework.presentation.model.NoteModel

data class NotesListViewState(
    val notesList: MutableList<NoteModel> = mutableListOf(),
    val isLoading: Boolean = false
)
