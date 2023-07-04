package com.example.homework.presentation.detail

import com.example.homework.presentation.composefutures.FIRST_THEME

data class NoteViewState(
    val userTitle: String = "",
    val userDescription: String = "",
    val userDate: String = "",
    val errorText: String = "",
    val exit: Boolean = false,
    val currentTheme: Int = FIRST_THEME,
    val loading: Boolean = false,
)
