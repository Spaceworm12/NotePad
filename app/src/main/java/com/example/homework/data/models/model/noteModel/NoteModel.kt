package com.example.homework.data.models.model.noteModel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteModel(
    val id: Long,
    val name: String,
    val description: String
) : Parcelable