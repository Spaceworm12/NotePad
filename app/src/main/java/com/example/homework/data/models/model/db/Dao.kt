package com.example.homework.data.models.model.db

import androidx.room.*
import androidx.room.Dao
import com.example.homework.data.models.model.db.entity.NoteEntity
import com.example.homework.util.Resource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao

interface Dao {
    @Query("SELECT * FROM all_notes")
    fun getAll(): Observable<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(example: NoteEntity): Single<Resource<Long>>

    @Query("DELETE FROM all_notes WHERE id = :id")
    fun delete(id: Long) : Completable

    @Query("UPDATE all_notes SET date = :date WHERE id = :id")
    fun changeDate(date: String, id: Long) : Single<Resource<Int>>
}


