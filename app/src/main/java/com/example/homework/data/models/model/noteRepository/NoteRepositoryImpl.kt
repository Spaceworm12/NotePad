package com.example.homework.data.models.model.noteRepository

import com.example.homework.data.models.model.db.DaoExample
import com.example.homework.data.models.model.db.entity.EntityExample
import com.example.homework.util.Resource


class NoteRepositoryImpl(private val daoExample:DaoExample) : NoteRepository {
    override suspend fun getItems(): Resource<List<EntityExample>> {

        //Пытаемся получить данные из БД, если не вышло, ловим ошибку
        val response = try {
            daoExample.getAllExamples()
        } catch(e: Exception) {
            //Оборачиваем ошибку в Resource
            return Resource.Error("Херня, Витя, загружай по новой.")
        }
        //Оборачиваем успешный результат в Resource
        return Resource.Success(response)

    }
    override suspend fun insertExample(example: EntityExample): Resource<Long> {

        val response = try {
            //Вернет id нового элемента
            daoExample.insertExample(example)
        } catch(e: Exception) {
            return Resource.Error("Херня, Витя, добавляй по новой.")
        }

        return Resource.Success(response)
    }
    override suspend fun deleteExample(id: Long): Resource<Unit> {

        val response = try {
            daoExample.deleteExample(id)
        } catch(e: Exception) {
            return Resource.Error("Херня, Витя, удаляй по новой.")
        }

        return Resource.Success(response)
    }
    }

