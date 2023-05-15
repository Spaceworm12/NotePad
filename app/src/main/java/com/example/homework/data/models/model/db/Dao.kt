package com.example.homework.data.models.model.db

import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {
    @Query("SELECT * FROM table")
    suspend fun getAll(): List<com.example.homework.data.models.model.db.entity.Entity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(note: com.example.homework.data.models.model.db.entity.Entity): Long

    @Query("DELETE FROM table WHERE id = :id")
    suspend fun delete(id: Long)
}
