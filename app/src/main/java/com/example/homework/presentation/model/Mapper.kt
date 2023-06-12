package com.example.homework.presentation.model

import com.example.homework.data.models.model.db.entity.NoteEntity
import com.example.homework.presentation.model.NoteModel as NoteModelPresentation

object Mapper {

    private fun transformToPresentation(model: NoteEntity): NoteModelPresentation {
        return NoteModelPresentation(
            id = model.id,
            name = model.name,
            description = model.description,
            type = model.type,
            date = model.date
        )
    }

    fun transformToPresentation(task: List<NoteEntity>): List<NoteModelPresentation> {
        return task.map { transformToPresentation(it) }
    }

    fun transformToData(model: NoteModelPresentation): NoteEntity {
        return NoteEntity(
            id = model.id,
            name = model.name,
            description = model.description,
            type = model.type,
            date = model.date
        )
    }

//    fun transformToType(type: String): NoteType {
//        return when (type) {
//            "NOTE_TYPE" -> NoteType.NOTE_TYPE
//            "BIRTHDAY_TYPE" -> NoteType.BIRTHDAY_TYPE
//            else -> NoteType.NOTE_TYPE
//        }
//    }
//
//    fun transformFromType(type: NoteType): String {
//        return when (type) {
//            NoteType.NOTE_TYPE -> "NOTE_TYPE"
//            NoteType.BIRTHDAY_TYPE -> "BIRTHDAY_TYPE"
//        }

    }

