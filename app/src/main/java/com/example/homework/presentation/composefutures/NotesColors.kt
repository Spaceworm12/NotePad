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
    notEnabled: Color
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

}

fun firstColors(
    primary: Color = Color(0xFF7400b8),
    primaryVariant: Color = Color(0XFF6930c3),
    secondary: Color = Color(0xFF5e60ce),
    secondaryVariant: Color = Color(0xFF5390d9),
    background: Color = Color(0xFF4ea8de),
    surface: Color = Color.White,
    error: Color = Color(0xFF48bfe3),
    onPrimary: Color = Color.Black,
    onSecondary: Color = Color.White,
    onBackground: Color = Color.Black,
    onSurface: Color = Color(0xFF356cfe1),
    onError: Color = Color.White,
    notEnabled: Color = Color(0xFF64dfdf),
    rippleColor: Color = Color(0XFF72efdd)
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
)

fun secondColors(
    primary: Color = Color(0xFF0466c8),
    primaryVariant: Color = Color(0XFF0353a4),
    secondary: Color = Color(0xFF023e7d),
    secondaryVariant: Color = Color(0xFF002855),
    background: Color = Color(0XFF001845),
    surface: Color = Color(0xFF001233),
    error: Color = Color(0xFF33415c),
    onPrimary: Color = Color.Black,
    onSecondary: Color = Color.Black,
    onBackground: Color = Color.White,
    onSurface: Color = Color.White,
    onError: Color = Color.Black,
    notEnabled: Color = Color(0xFF5c677d),
    rippleColor: Color = Color(0XFF7d8597),
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
)

fun thirdColors(
    primary: Color = Color(0xFFe0aaff),
    primaryVariant: Color = Color(0XFFc77dff),
    secondary: Color = Color(0xFF9d4edd),
    secondaryVariant: Color = Color(0xFF7b2cbf),
    background: Color = Color(0XFF5a189a),
    surface: Color = Color(0xFF3c096c),
    error: Color = Color(0xFF240046),
    onPrimary: Color = Color.Black,
    onSecondary: Color = Color.Black,
    onBackground: Color = Color.White,
    onSurface: Color = Color.White,
    onError: Color = Color.Black,
    notEnabled: Color = Color(0xFF10002b),
    rippleColor: Color = Color(0XFF10002b),
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
)
