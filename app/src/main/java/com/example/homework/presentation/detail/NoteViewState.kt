package com.example.homework.presentation.detail

data class NoteViewState(
    val userTitle: String = "", val userDescription: String = "", val userDate: String = "",
    val errorText: String = "", val exit: Boolean = false
)
