package com.example.homework.data.models.model.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.homework.presentation.model.NoteType

const val TABLE_NOTES = "all_notes"

@Entity(tableName = TABLE_NOTES)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val description: String,
    val type: NoteType,
    val date: String
)








