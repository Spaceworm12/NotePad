package com.example.homework.data.models.model.noteRepository

import com.example.homework.data.models.model.db.ExampleDao
import com.example.homework.data.models.model.db.entity.ExampleEntity
import com.example.homework.data.models.model.noteModel.NoteModel
import com.example.homework.data.models.model.util.Resource


class NoteRepositoryImpl(private val exampleDao: ExampleDao) : NoteRepository {
    override suspend fun getItems(): Resource<List<ExampleEntity>> {

        //Пытаемся получить данные из БД, если не вышло, ловим ошибку
        val response = try {
            exampleDao.getAllExamples()
        } catch(e: Exception) {
            //Оборачиваем ошибку в Resource
            return Resource.Error("Херня, Витя, загружай по новой.")
        }
        //Оборачиваем успешный результат в Resource
        return Resource.Success(response)

    }
    override suspend fun insertExample(example: ExampleEntity): Resource<Long> {

        val response = try {
            //Вернет id нового элемента
            exampleDao.insertExample(example)
        } catch(e: Exception) {
            return Resource.Error("Херня, Витя, добавляй по новой.")
        }

        return Resource.Success(response)
    }
    override suspend fun deleteExample(id: Long): Resource<Unit> {

        val response = try {
            exampleDao.deleteExample(id)
        } catch(e: Exception) {
            return Resource.Error("Херня, Витя, удаляй по новой.")
        }

        return Resource.Success(response)
    }
    override fun getNotes(): List<NoteModel> {
        return listOf(
            NoteModel(
                id = 1,
                name = "Сходить в магазин",
                description = "Описание второго"
            ),
            NoteModel(
                id = 2,
                name = "Сходить в магазин",
                description = "Описание второго"
            ),
            NoteModel(
                id = 3,
                name = "Покормить попугая",
                description = "Описание третьего"
            ),
            NoteModel(
                id = 4,
                name = "Сделать гимнастику",
                description = "Описание четвертого"
            ),
            NoteModel(id = 5, name = "Посмотреть график", description = "Описание пятого"),
            NoteModel(id = 6, name = "Купить хлеба", description = "Описание шестого"),
            NoteModel(
                id = 7,
                name = "Сходить в магазин",
                description = "Описание седьмого"
            ),
            NoteModel(
                id = 8,
                name = "Покормить попугая",
                description = "Описание восьмого"
            ),
            NoteModel(
                id = 9,
                name = "Сделать гимнастику",
                description = "Описание девятого"
            ),
            NoteModel(
                id = 10,
                name = "Посмотреть график",
                description = "Описание десятого"
            ),
            NoteModel(
                id = 11,
                name = "Купить хлеба",
                description = "Описание одиннадцатого"
            ),
            NoteModel(
                id = 12,
                name = "Сходить в магазин",
                description = "Описание двенадцатого"
            ),

            NoteModel(
                id = 13,
                name = "Покормить попугая",
                description = "Описание тринадцатого"
            ),
            NoteModel(
                id = 14,
                name = "Сделать гимнастику",
                description = "Описание четырнадцатого"
            ),
            NoteModel(
                id = 15,
                name = "Посмотреть график",
                description = "Описание пятнадцатого"
            )
        )
    }

}