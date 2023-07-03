package com.example.homework.data.models.model.noterepository

import com.example.homework.data.models.model.db.entity.NoteEntity
import com.example.homework.util.Resource
import io.reactivex.Observable

interface Repository {
     fun getAll(): Observable<Resource<List<NoteEntity>>>
     fun create(entity: NoteEntity): Observable<Resource<Long>>
     fun delete(id: Long): Observable<Resource<Unit>>
     fun deleteAll(): Observable<Resource<Unit>>
     fun changeDate(date: String, id: Long): Observable<Resource<Long>>
}
