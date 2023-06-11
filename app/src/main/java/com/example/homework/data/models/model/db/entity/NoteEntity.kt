package com.example.homework.data.models.model.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

const val TABLE_NOTES = "all_notes"

@Entity(tableName = TABLE_NOTES)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val description: String,
    val date: String,
    val type: String
)







