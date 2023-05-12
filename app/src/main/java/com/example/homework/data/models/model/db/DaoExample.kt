package com.example.homework.data.models.model.db

import androidx.room.*
import com.example.homework.data.models.model.db.entity.EntityExample

@Dao

interface DaoExample {


    @Query("SELECT * FROM my_table")
    suspend fun getAllExamples(): List<EntityExample>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExample(example: EntityExample): Long

    @Query("DELETE FROM my_table WHERE id = :id")
    suspend fun deleteExample(id: Long)

}