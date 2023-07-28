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
    bigDimension: Dp,
    someOneDimension: Dp,
) {
    var sideMargin by mutableStateOf(sideMargin, structuralEqualityPolicy())
        internal set
    var inputsMargin by mutableStateOf(inputsMargin, structuralEqualityPolicy())
        internal set
    var contentMargin by mutableStateOf(contentMargin, structuralEqualityPolicy())
        internal set
    var halfContentMargin by mutableStateOf(halfContentMargin, structuralEqualityPolicy())
        internal set
    var bigDimension by mutableStateOf(bigDimension, structuralEqualityPolicy())
        internal set
    var someOneDimension by mutableStateOf(someOneDimension, structuralEqualityPolicy())
        internal set
}

fun normalDimensions(
    sideMargin: Dp = 16.dp,
    inputsMargin: Dp = 24.dp,
    contentMargin: Dp = 8.dp,
    halfContentMargin: Dp = 4.dp,
    bigDimension: Dp = 70.dp,
    someOneDimension: Dp = 150.dp
): NotesDimens = NotesDimens(
    sideMargin = sideMargin,
    inputsMargin = inputsMargin,
    contentMargin = contentMargin,
    halfContentMargin = halfContentMargin,
    bigDimension = bigDimension,
    someOneDimension=someOneDimension
)

fun smallDimensions(
    sideMargin: Dp = 12.dp,
    inputsMargin: Dp = 20.dp,
    contentMargin: Dp = 6.dp,
    halfContentMargin: Dp = 3.dp,
    bigDimension: Dp = 50.dp,
    someOneDimension: Dp = 80.dp

): NotesDimens = NotesDimens(
    sideMargin = sideMargin,
    inputsMargin = inputsMargin,
    contentMargin = contentMargin,
    halfContentMargin = halfContentMargin,
    bigDimension = bigDimension,
    someOneDimension=someOneDimension

)
fun bigDimensions(
    sideMargin: Dp = 20.dp,
    inputsMargin: Dp = 28.dp,
    contentMargin: Dp = 10.dp,
    halfContentMargin: Dp = 6.dp,
    bigDimension: Dp = 100.dp,
    someOneDimension: Dp = 150.dp

    ): NotesDimens = NotesDimens(
    sideMargin = sideMargin,
    inputsMargin = inputsMargin,
    contentMargin = contentMargin,
    halfContentMargin = halfContentMargin,
    bigDimension = bigDimension,
    someOneDimension=someOneDimension
)
