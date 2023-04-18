package com.example.homework

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val id: Long,
    val name: String,
    val description: String
): Parcelable