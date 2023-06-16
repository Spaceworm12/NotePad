package com.example.homework.data.models.model.noteRepository

import com.example.homework.data.models.model.db.Dao
import com.example.homework.data.models.model.db.Db
import com.example.homework.data.models.model.db.entity.NoteEntity
import com.example.homework.util.Resource

class RepositoryImplement(private val dao: Dao, private val db: Db) : Repository {

    override suspend fun getAll(): Resource<List<NoteEntity>> {
        val response = try {
            dao.getAll()
        } catch (e: Exception) {
            return Resource.Error("НЕ ПОЛУЧАЕТСЯ")
        }
        return Resource.Success(response)
    }

    override suspend fun create(entity: NoteEntity): Resource<Long> {
        val response = try {
            dao.create(entity)
        } catch (e: Exception) {
            return Resource.Error("НЕ ДОБАВЛЯЕТСЯ)")
        }
        return Resource.Success(response)
    }

    override suspend fun delete(id: Long): Resource<Unit> {
        val response = try {
            dao.delete(id)
        } catch (e: Exception) {
            return Resource.Error("НЕ УДАЛЯЕТСЯ")
        }
        return Resource.Success(response)
    }

    override suspend fun deleteAll(): Resource<Unit> {
        val response = try {
            db.clearAllTables()
        } catch (e: Exception) {
            return Resource.Error("НЕ УДАЛЯЕТСЯ")
        }
        return Resource.Success(response)
    }

    override suspend fun changeDate(date: String, id: Long): Resource<Unit> {
        val response = try {
            dao.changeDate(date, id)
        } catch (e: Exception) {
            return Resource.Error("НЕ УДАЛЯЕТСЯ")
        }
        return Resource.Success(response)
    }
}




