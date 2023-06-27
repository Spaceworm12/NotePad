package com.example.homework.presentation.SwitchTheme

sealed class SelectThemeEvents {
    object Exit : SelectThemeEvents()
    class SwitchTheme(val theme: Int) : SelectThemeEvents()
//    object CheckIn : SelectThemeEvents()
}