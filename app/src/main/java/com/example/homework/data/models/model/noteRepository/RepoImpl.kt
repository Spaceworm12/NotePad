package com.example.homework.data.models.model.noteRepository

import com.example.homework.data.models.model.db.Dao
import com.example.homework.data.models.model.db.entity.MyEntity
import com.example.homework.util.Resource

class RepoImpl(private val dao: Dao): Repo {

    override suspend fun getAll(): Resource<List<MyEntity>> {
        val response = try {
            dao.getAll()
        }catch (e:Exception) {
            return Resource.Error("НЕ ПОЛУЧАЕТСЯ")
        }
        return Resource.Success(response)
    }

    override suspend fun create(example: MyEntity): Resource<Long> {
        val response = try {
            dao.create(example)
        }catch (e:Exception) {
            return Resource.Error("НЕ ДОБАВЛЯЕТСЯ)")
        }
        return Resource.Success(response)
    }

    override suspend fun delete(id: Long): Resource<Unit> {
        val response = try {
            dao.delete(id)
        }catch (e:Exception) {
            return Resource.Error("НЕ УДАЛЯЕТСЯ")
        }
        return Resource.Success(response)
    }

}

