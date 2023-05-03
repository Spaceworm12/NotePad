package com.example.homework.presentation.recycler

import com.example.homework.presentation.model.NoteModel

//Что такое sealed class сам почитай
sealed class RecyclerEvent {
    //Здесь хранятся все наши события из View(фрагмента)

    //Если нам не требуются входные данные используем object
    object GetNotes : RecyclerEvent()

    class AddNote(val note: NoteModel) : RecyclerEvent()

    class DeleteNote(val note: NoteModel, val index: Int) : RecyclerEvent()
}
