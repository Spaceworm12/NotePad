package com.example.homework.data.models.model.noteRepository

import com.example.homework.data.models.model.db.entity.Entity
import com.example.homework.util.Resource

interface Repo {

    suspend fun getAll(): Resource<List<Entity>>
    suspend fun create(note: Entity): Resource<Long>
    suspend fun delete(id: Long): Resource<Unit>
}
