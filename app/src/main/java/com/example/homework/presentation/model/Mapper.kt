package com.example.homework.presentation.model

import com.example.homework.data.models.model.db.entity.MyEntity
import com.example.homework.presentation.model.NoteModel as ExampleNoteModelPresentation

object Mapper {

    private fun transformToPresentation(model: MyEntity): ExampleNoteModelPresentation {
        return ExampleNoteModelPresentation(
            id = model.id,
            name = model.name,
            description = model.description
        )
    }

    fun transformToPresentation(task: List<MyEntity>): List<ExampleNoteModelPresentation> {
        return task.map { transformToPresentation(it) }
    }

    fun transformToData(model: ExampleNoteModelPresentation): MyEntity {
        return MyEntity(
            id = model.id,
            name = model.name,
            description = model.description
        )
    }

}
