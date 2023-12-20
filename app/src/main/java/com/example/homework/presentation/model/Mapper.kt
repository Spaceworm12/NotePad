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

}

