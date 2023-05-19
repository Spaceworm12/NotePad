package com.example.homework.data.models.model.db

import androidx.room.Dao
import androidx.room.*
import com.example.homework.data.models.model.app.ApplicationDb
import com.example.homework.data.models.model.db.entity.MyEntity

@Dao

interface Dao {


    @Query("SELECT * FROM my_table")
    suspend fun getAll(): List<MyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(example: MyEntity): Long

    @Query("DELETE FROM my_table WHERE id = :id")
    suspend fun delete(id: Long)

    }


