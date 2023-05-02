package com.example.homework.data.models.model.noteRepository

import com.example.homework.data.models.model.noteModel.NoteModel


class NoteRepositoryImpl : NoteRepository {
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