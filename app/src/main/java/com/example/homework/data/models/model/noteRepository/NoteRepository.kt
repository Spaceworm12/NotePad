package com.example.homework.data.models.model.noteRepository

import com.example.homework.data.models.model.db.entity.EntityExample
import com.example.homework.data.models.model.noteModel.NoteModel
import com.example.homework.util.Resource

interface NoteRepository {
    suspend fun getItems(): Resource<List<EntityExample>>

    suspend fun insertExample(example: EntityExample): Resource<Long>

    suspend fun deleteExample(id: Long): Resource<Unit>
}