package com.example.homework.presentation.SwitchTheme

sealed class SelectThemeEvents {
    object Exit : SelectThemeEvents()
    class SwitchTheme(val theme: Int) : SelectThemeEvents()
    class CheckIn(val theme: Int) : SelectThemeEvents()
}