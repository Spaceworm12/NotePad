package com.example.homework.presentation.selectTheme

sealed class SelectThemeEvents {
    object Exit : SelectThemeEvents()
    class SwitchTheme(val theme: Int) : SelectThemeEvents()
}