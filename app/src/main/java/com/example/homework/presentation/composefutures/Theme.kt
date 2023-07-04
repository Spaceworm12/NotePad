package com.example.homework.presentation.composefutures

import LocalAppColors
import LocalAppDimens
import LocalAppShapes
import LocalAppTypography
import NotesTheme
import WrsRippleTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

const val THEME_CODE = "THEME_CODE"
const val FIRST_THEME = 0
const val SECOND_THEME = 1
const val THIRD_THEME = 2

private val FirstThemePalette = firstColors()
private val SecondThemePalette = secondColors()
private val ThirdThePalette = thirdColors()

@Composable
fun ThemeSettings(
    themeCode: Int = 0,
    content: @Composable () -> Unit
) {

    val colors = when (themeCode) {
        FIRST_THEME -> {
            FirstThemePalette
        }
        SECOND_THEME -> {
            SecondThemePalette
        }
        else -> {
            ThirdThePalette
        }
    }

    val dimensions: NotesDimens
    val typography: NotesTypography

    when (themeCode) {
        FIRST_THEME -> {
            dimensions = normalDimensions()
            typography = normalTypography()
        }
        SECOND_THEME -> {
            dimensions = smallDimensions()
            typography = cursiveTypography()
        }
        THIRD_THEME -> {
            dimensions = smallDimensions()
            typography = cursiveTypography()
        }
        else -> {
            dimensions = smallDimensions()
            typography = cursiveTypography()
        }
    }

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppTypography provides typography,
        LocalAppShapes provides NotesTheme.shapes,
        LocalRippleTheme provides WrsRippleTheme,
        LocalAppDimens provides dimensions,
        content = content,
    )

}
