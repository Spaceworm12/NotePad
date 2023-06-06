package com.example.homework.data.models.model.noteRepository

import com.example.homework.data.models.model.db.entity.NoteEntity
import com.example.homework.util.Resource

interface Repository {

    suspend fun getAll(): Resource<List<NoteEntity>>
    suspend fun create(entity: NoteEntity): Resource<Long>
    suspend fun delete(id: Long): Resource<Unit>
    suspend fun deleteAll(): Resource<Unit>

}
