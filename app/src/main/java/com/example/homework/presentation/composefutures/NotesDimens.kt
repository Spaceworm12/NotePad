package com.example.homework.presentation.composefutures

import androidx.compose.runtime.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
class NotesDimens(
    sideMargin: Dp,
    inputsMargin: Dp,
    contentMargin: Dp,
    halfContentMargin: Dp,
) {
    var sideMargin by mutableStateOf(sideMargin, structuralEqualityPolicy())
        internal set
    var inputsMargin by mutableStateOf(inputsMargin, structuralEqualityPolicy())
        internal set
    var contentMargin by mutableStateOf(contentMargin, structuralEqualityPolicy())
        internal set
    var halfContentMargin by mutableStateOf(halfContentMargin, structuralEqualityPolicy())
        internal set
}

fun normalDimensions(
    sideMargin: Dp = 16.dp,
    inputsMargin: Dp = 24.dp,
    contentMargin: Dp = 8.dp,
    halfContentMargin: Dp = 4.dp,
): NotesDimens = NotesDimens(
    sideMargin = sideMargin,
    inputsMargin = inputsMargin,
    contentMargin = contentMargin,
    halfContentMargin = halfContentMargin,
)

fun smallDimensions(
    sideMargin: Dp = 12.dp,
    inputsMargin: Dp = 20.dp,
    contentMargin: Dp = 6.dp,
    halfContentMargin: Dp = 3.dp,

): NotesDimens = NotesDimens(
    sideMargin = sideMargin,
    inputsMargin = inputsMargin,
    contentMargin = contentMargin,
    halfContentMargin = halfContentMargin,
)
