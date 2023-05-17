package com.example.homework.data.models.model.noteRepository

import com.example.homework.data.models.model.db.entity.MyEntity
import com.example.homework.util.Resource

interface Repo {

    suspend fun getAll(): Resource<List<MyEntity>>
    suspend fun create(entity: MyEntity): Resource<Long>
    suspend fun delete(id: Long): Resource<Unit>
}
