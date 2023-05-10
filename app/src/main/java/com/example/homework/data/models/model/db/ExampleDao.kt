package com.example.homework.data.models.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.homework.data.models.model.db.entity.ExampleEntity

@Dao

interface ExampleDao{


    @Query("SELECT * FROM example_table")
    suspend fun getAllExamples():List<ExampleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExample(example: ExampleEntity) : Long

    @Query("DELETE FROM example_table WHERE id = :id")
    suspend fun deleteExample(id:Long)
}