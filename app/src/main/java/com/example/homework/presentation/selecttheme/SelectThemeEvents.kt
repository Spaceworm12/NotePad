package com.example.homework.presentation.selecttheme

sealed class SelectThemeEvents {
    object Exit : SelectThemeEvents()
    class SwitchTheme(val theme: Int) : SelectThemeEvents()
}