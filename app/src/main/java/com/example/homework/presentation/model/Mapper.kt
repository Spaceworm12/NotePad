package com.example.homework.presentation.model

import com.example.homework.data.models.model.db.entity.MyEntity
import com.example.homework.presentation.model.NoteModel as NoteModelPresentation

object Mapper {

    private fun transformToPresentation(model: MyEntity): NoteModelPresentation {
        return NoteModelPresentation(
            id = model.id,
            name = model.name,
            description = model.description
        )
    }

    fun transformToPresentation(task: List<MyEntity>): List<NoteModelPresentation> {
        return task.map { transformToPresentation(it) }
    }

    fun transformToData(model: NoteModelPresentation): MyEntity {
        return MyEntity(
            id = model.id,
            name = model.name,
            description = model.description
        )
    }

}
