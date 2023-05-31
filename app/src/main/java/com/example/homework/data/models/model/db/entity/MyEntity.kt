package com.example.homework.data.models.model.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

const val ALL_NOTES = "table_with_notes"

@Entity(tableName = ALL_NOTES)
data class MyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val description: String
)







