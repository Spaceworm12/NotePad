package com.example.homework.data.models.model.db

import androidx.room.Dao
import androidx.room.*
import com.example.homework.data.models.model.db.entity.MyEntity

@Dao

interface Dao {


    @Query("SELECT * FROM table_with_notes")
    suspend fun getAll(): List<MyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(example: MyEntity): Long

    @Query("DELETE FROM table_with_notes WHERE id = :id")
    suspend fun delete(id: Long)

    }


