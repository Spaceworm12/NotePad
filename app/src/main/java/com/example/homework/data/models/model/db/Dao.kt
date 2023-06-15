package com.example.homework.data.models.model.db

import androidx.room.*
import androidx.room.Dao
import com.example.homework.data.models.model.db.entity.NoteEntity

@Dao

interface Dao {


    @Query("SELECT * FROM all_notes")
    suspend fun getAll(): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(example: NoteEntity): Long

    @Query("DELETE FROM all_notes WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("UPDATE all_notes SET date = :date WHERE id = :id")
    suspend fun changeDate(date: String, id: Long)

}


