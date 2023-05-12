package com.example.homework.data.models.model.noteRepository

import com.example.homework.data.models.model.db.DaoExample
import com.example.homework.data.models.model.db.entity.EntityExample
import com.example.homework.util.Resource


class NoteRepositoryImpl(private val daoExample: DaoExample) : NoteRepository {
    override suspend fun getItems(): Resource<List<EntityExample>> {
        val response = try {
            daoExample.getAllExamples()
        } catch (e: Exception) {
            return Resource.Error("чет не срослось")
        }
        return Resource.Success(response)

    }

    override suspend fun insertExample(example: EntityExample): Resource<Long> {

        val response = try {
            daoExample.insertExample(example)
        } catch (e: Exception) {
            return Resource.Error("чет не срослось")
        }

        return Resource.Success(response)
    }

    override suspend fun deleteExample(id: Long): Resource<Unit> {

        val response = try {
            daoExample.deleteExample(id)
        } catch (e: Exception) {
            return Resource.Error("чет не срослось")
        }

        return Resource.Success(response)
    }
}

