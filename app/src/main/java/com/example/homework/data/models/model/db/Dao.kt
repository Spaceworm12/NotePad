package com.example.homework.data.models.model.db

import androidx.room.Dao
import androidx.room.*
import com.example.homework.data.models.model.db.entity.MyEntity

@Dao
interface Dao {
    @Query("SELECT * FROM table")
    suspend fun getAll(): List<MyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(entity: MyEntity): Long

    @Query("DELETE FROM table WHERE id = :id")
    suspend fun delete(id: Long)

//    @Query("DELETE * FROM table")
//    suspend fun deleteAll()
}
