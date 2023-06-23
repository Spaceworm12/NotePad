package com.example.homework.data.models.model.noteRepository

import androidx.room.Entity
import com.example.homework.data.models.model.db.entity.NoteEntity
import com.example.homework.util.Resource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface Repository {
     fun getAll(): Observable<Resource<List<NoteEntity>>>
     fun create(entity: NoteEntity): Observable<Resource<Long>>
     fun delete(id: Long): Observable<Resource<Unit>>
     fun deleteAll(): Observable<Resource<Unit>>
     fun changeDate(date: String, id: Long): Observable<Resource<Long>>
}
