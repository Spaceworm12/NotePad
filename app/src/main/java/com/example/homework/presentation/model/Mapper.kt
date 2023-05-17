package com.example.homework.presentation.model

import com.example.homework.data.models.model.db.entity.NoteModel
import com.example.homework.presentation.model.NoteModel as ExampleNoteModelPresentation

object Mapper {

    private fun transformToPresentation(model: NoteModel): ExampleNoteModelPresentation {
        return ExampleNoteModelPresentation( dsa
            id = model.id,
            name = model.name,
            description = model.description
        )
    }

    fun transformToPresentation(task: List<EntityExample>): List<ExampleNoteModelPresentation> {
        return task.map { transformToPresentation(it) }
    }

    fun transformToData(model: ExampleNoteModelPresentation): EntityExample {
        return EntityExample(
            id = model.id,
            name = model.name,
            description = model.description
        )
    }

}
