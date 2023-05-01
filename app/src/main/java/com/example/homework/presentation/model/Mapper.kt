package com.example.homework.presentation.model


//переназначаем имя ExampleModel из слоя презентации, чтобы не было путаницы
import com.example.homework.presentation.model.NoteModel as ExampleModelPresentation

//В каждом слое мы используем свою модель,
// поэтому нам нужно конвертировать ExampleModel из data в ExampleModel из presentation слоя
object Mapper {

    private fun transformToPresentation(noteModel: ExampleModelPresentation): ExampleModelPresentation {
        return ExampleModelPresentation(
            id = noteModel.id,
            name = noteModel.name,
            description = noteModel.description
        )
    }

    //Трансформировать будем в месте получения данных, т.е. во ViewModel
    fun transformToPresentation(task: List<ExampleModelPresentation>): List<ExampleModelPresentation> {
        return task.map { transformToPresentation(it) }
    }

}
