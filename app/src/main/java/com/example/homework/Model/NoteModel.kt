package com.example.homework.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteModel(
    val id: Long,
    val name: String,
    val description: String
): Parcelable