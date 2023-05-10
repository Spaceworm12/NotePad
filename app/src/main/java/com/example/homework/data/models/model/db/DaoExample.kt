package com.example.homework.data.models.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.homework.data.models.model.db.entity.EntityExample

@Dao

interface DaoExample{


    @Query("SELECT * FROM table")
    suspend fun getAllExamples():List<EntityExample>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExample(example: EntityExample) : Long

    @Query("DELETE FROM table WHERE id = :id")
    suspend fun deleteExample(id:Long)

//    @Query("SELECT * FROM table WHERE id = :id")
 //   suspend fun getExampleById(id: Long): EntityExample
}