package com.example.homework.presentation.recycler

import com.example.homework.presentation.model.NoteModel


//По сути обычный класс данных, используется для удобства, если в liveData больше чем одно поле
data class RecyclerViewState(
    val notesList: MutableList<NoteModel> = mutableListOf(),
    val isLoading: Boolean = false
)
