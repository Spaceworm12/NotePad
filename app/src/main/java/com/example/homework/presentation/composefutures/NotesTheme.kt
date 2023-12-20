package com.example.homework.presentation.composefutures
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

object NotesTheme {
    val colors: NotesColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val typography: NotesTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current

    val shapes: NotesShape
        @Composable
        @ReadOnlyComposable
        get() = LocalAppShapes.current

    val dimens: NotesDimens
        @Composable
        @ReadOnlyComposable
        get() = LocalAppDimens.current

}

val LocalAppColors = staticCompositionLocalOf<NotesColors> {
    error("No colors provided")
}

val LocalAppTypography = staticCompositionLocalOf<NotesTypography> {
    error("No typography provided")
}

val LocalAppShapes = staticCompositionLocalOf {
    NotesShape()
}

val LocalAppDimens = staticCompositionLocalOf<NotesDimens> {
    error("No dimensions provided")
}

@Immutable
internal object WrsRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = NotesTheme.colors.rippleColor

    @Composable
    override fun rippleAlpha() = RippleAlpha(
        draggedAlpha = 1f,
        focusedAlpha = 1f,
        hoveredAlpha = 1f,
        pressedAlpha = 1f,
    )
}
