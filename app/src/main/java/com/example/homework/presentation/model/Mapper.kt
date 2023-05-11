package com.example.homework.presentation.model

import com.example.homework.data.models.model.db.entity.EntityExample
import com.example.homework.data.models.model.noteModel.NoteModel
import com.example.homework.presentation.model.NoteModel as ExampleNoteModelPresentation

object Mapper {

    private fun transformToPresentation(noteModel: EntityExample): ExampleNoteModelPresentation {
        return ExampleNoteModelPresentation(
            id = noteModel.id,
            name = noteModel.name,
            description = noteModel.description
        )
    }

    fun transformToPresentation(task: List<EntityExample>): List<ExampleNoteModelPresentation> {
        return task.map { transformToPresentation(it) }
    }

    fun transformToData(model: NoteModel): EntityExample { //model:ExampleNoteModelPresentation???
        return EntityExample(
            id = model.id,
            name = model.name,
            description = model.description
        )
    }

}
