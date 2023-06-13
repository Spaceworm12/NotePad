package com.example.homework.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteModel(
    val id: Long,
    val name: String,
    var description: String,
    val type: NoteType,
    var date: String
) : Parcelable

enum class NoteType{
    NOTE_TYPE,
    BIRTHDAY_TYPE
}
