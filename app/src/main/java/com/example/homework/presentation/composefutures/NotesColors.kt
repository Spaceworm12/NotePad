package com.example.homework.presentation.composefutures

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Stable
class NotesColors(
    primary: Color,
    primaryVariant: Color,
    secondary: Color,
    secondaryVariant: Color,
    background: Color,
    surface: Color,
    error: Color,
    onPrimary: Color,
    onSecondary: Color,
    onBackground: Color,
    onSurface: Color,
    onError: Color,
    rippleColor: Color,
    notEnabled: Color,
    custom: Color,
) {
    var primary by mutableStateOf(primary, structuralEqualityPolicy())
        internal set
    var primaryVariant by mutableStateOf(primaryVariant, structuralEqualityPolicy())
        internal set
    var secondary by mutableStateOf(secondary, structuralEqualityPolicy())
        internal set
    var secondaryVariant by mutableStateOf(secondaryVariant, structuralEqualityPolicy())
        internal set
    var background by mutableStateOf(background, structuralEqualityPolicy())
        internal set
    var surface by mutableStateOf(surface, structuralEqualityPolicy())
        internal set
    var error by mutableStateOf(error, structuralEqualityPolicy())
        internal set
    var onPrimary by mutableStateOf(onPrimary, structuralEqualityPolicy())
        internal set
    var onSecondary by mutableStateOf(onSecondary, structuralEqualityPolicy())
        internal set
    var onBackground by mutableStateOf(onBackground, structuralEqualityPolicy())
        internal set
    var onSurface by mutableStateOf(onSurface, structuralEqualityPolicy())
        internal set
    var onError by mutableStateOf(onError, structuralEqualityPolicy())
        internal set
    var rippleColor by mutableStateOf(rippleColor, structuralEqualityPolicy())
        internal set
    var notEnabled by mutableStateOf(notEnabled, structuralEqualityPolicy())
        internal set
    var custom by mutableStateOf(custom, structuralEqualityPolicy())
        internal set

}

fun firstColors(
    primary: Color = Color(0xFF5e60ce),
    primaryVariant: Color = Color(0xFF5e60ce),
    secondary: Color = Color(0xFF5390d9),
    secondaryVariant: Color = Color(0xFF4ea8de),
    background: Color = Color(0xFF48bfe3),
    surface: Color = Color.White,
    error: Color = Color(0xFF356cfe1),
    onPrimary: Color = Color.Black,
    onSecondary: Color = Color.White,
    onBackground: Color = Color.Black,
    onSurface: Color = Color(0xFF64dfdf),
    onError: Color = Color.White,
    notEnabled: Color = Color(0XFF72efdd),
    rippleColor: Color = Color(0XFF72efdd),
    custom:Color = Color(0xFF00E9C7)
): NotesColors = NotesColors(
    primary = primary,
    primaryVariant = primaryVariant,
    secondary = secondary,
    secondaryVariant = secondaryVariant,
    background = background,
    surface = surface,
    error = error,
    onPrimary = onPrimary,
    onSecondary = onSecondary,
    onBackground = onBackground,
    onSurface = onSurface,
    onError = onError,
    rippleColor = rippleColor,
    notEnabled = notEnabled,
    custom=custom,
)

fun secondColors(
    primary: Color = Color(0xFF5AACFF),
    primaryVariant: Color = Color(0xFF2E94FD),
    secondary: Color = Color(0xFF88A3C0),
    secondaryVariant: Color = Color(0xFFB8BDC9),
    background: Color = Color(0xFFCECECE),
    surface: Color = Color(0xFF0F1F3A),
    error: Color = Color(0xFF000000),
    onPrimary: Color = Color.Black,
    onSecondary: Color = Color.Black,
    onBackground: Color = Color.White,
    onSurface: Color = Color.White,
    onError: Color = Color.Black,
    notEnabled: Color = Color(0xFF7287B1),
    rippleColor: Color = Color(0xFF5D7AB9),
    custom:Color = Color(0xFF0D4FDF)
): NotesColors = NotesColors(
    primary,
    primaryVariant,
    secondary,
    secondaryVariant,
    background,
    surface,
    error,
    onPrimary,
    onSecondary,
    onBackground,
    onSurface,
    onError,
    rippleColor,
    notEnabled,
    custom,
)

fun thirdColors(
    primary: Color = Color(0xFFE9C2FF),
    primaryVariant: Color = Color(0xFFCE93FC),
    secondary: Color = Color(0xFFC276FF),
    secondaryVariant: Color = Color(0xFFA550EE),
    background: Color = Color(0xFF984AE4),
    surface: Color = Color(0xFFA13FF0),
    error: Color = Color(0xFF8F27E9),
    onPrimary: Color = Color.Black,
    onSecondary: Color = Color.Black,
    onBackground: Color = Color.White,
    onSurface: Color = Color.White,
    onError: Color = Color.Black,
    notEnabled: Color = Color(0xFF771AC7),
    rippleColor: Color = Color(0xFF5A0AA8),
    custom:Color = Color(0xFF7B0CE7)
): NotesColors = NotesColors(
    primary,
    primaryVariant,
    secondary,
    secondaryVariant,
    background,
    surface,
    error,
    onPrimary,
    onSecondary,
    onBackground,
    onSurface,
    onError,
    rippleColor,
    notEnabled,
    custom
)
