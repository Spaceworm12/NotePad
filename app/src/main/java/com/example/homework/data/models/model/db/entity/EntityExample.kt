package com.example.homework.data.models.model.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

private const val TABLE_NAME = "example_table"

@Entity(tableName = TABLE_NAME)
data class EntityExample(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val description: String
)