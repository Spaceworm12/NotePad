package com.example.homework.data.models.model.noteRepository

import com.example.homework.data.models.model.db.entity.ExampleEntity
import com.example.homework.data.models.model.noteModel.NoteModel
import com.example.homework.data.models.model.util.Resource

interface NoteRepository {
    suspend fun getItems(): Resource<List<ExampleEntity>>
    suspend fun insertExample(example: ExampleEntity): Resource<Long>
    suspend fun deleteExample(id: Long): Resource<Unit>
    fun getNotes(): List<NoteModel>
}