package com.example.homework.presentation.SwitchTheme

import com.example.homework.data.models.model.app.DbNotes

private const val THEME_CODE = "THEME_CODE"

data class SelectThemeViewState(val exit: Boolean = false, val currentTheme: Int = 0)
