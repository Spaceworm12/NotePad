package com.example.homework.presentation.recycler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework.data.models.model.noteRepository.NoteRepository
import com.example.homework.data.models.model.noteRepository.NoteRepositoryImpl
import com.example.homework.presentation.model.Mapper
import com.example.homework.presentation.model.NoteModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class RecyclerViewModel(
    //Создаем экземпляр именно интерфейса ItemRepository, но инициализируем классом, который его имплемиентирует,
    // так мы сможешь в любой момент изменить реализацию, почти не затрагивая слой презентации
    private val noteRepository: NoteRepository = NoteRepositoryImpl()
) : ViewModel() {

    //Инициализируем нашу LiveData, именно за ее изменениями следит наша View(фрагмент)
    //Про LiveData на самостоятельно изучение
    //В данном случае мы кладет туда RecyclerViewState - наш класс данных, но тут может и просто Booleen или String
    // private val _viewStateLoading = MutableLiveData(Boolean)- как пример
    private val _viewState = MutableLiveData(RecyclerViewState())
    val viewStateObs: LiveData<RecyclerViewState> get() = _viewState
    private var viewState: RecyclerViewState
        get() = _viewState.value!!
        set(value) {
            _viewState.value = value
        }

    fun submitUIEvent(event: RecyclerEvent) {
        handleUIEvent(event)
    }

    private fun handleUIEvent(event: RecyclerEvent) {
        when (event) {
            //Обработка ивента типа object
            RecyclerEvent.GetNotes -> getListNotes()
            //Обработка ивента типа class
            is RecyclerEvent.AddNote -> addNewNote(item = event.note)
            is RecyclerEvent.DeleteNote -> deleteNote(note = event.note, index = event.index)
        }
    }

    //Все функции viewModel приватные, кроме submitUIEvent
    private fun getListNotes() {
        // Не обращай внимания на viewModelScope.launch, это обсудим позже, пока пусть будет магией
        // Нам это нужно сейчас чтобы задержку к 1.5 секунды поставить, для имитации загрузки
        viewModelScope.launch {
            // Запускаем отображение нашей загрузки
            viewState = viewState.copy(isLoading = true)
            delay(1500)
            viewState = viewState.copy(
                notesList = Mapper.transformToPresentation(noteRepository.getNotes())
                    .toMutableList(),
                isLoading = false
            )
        }
    }

    private fun addNewNote(item: NoteModel) {
        val currentNotes = viewState.notesList
        currentNotes.add(item)
        viewState = viewState.copy(notesList = currentNotes)
    }

    private fun deleteNote(note: NoteModel, index: Int) {
        val correctNotes = viewState.notesList
        correctNotes.removeAt(index)
        viewState = viewState.copy(notesList = correctNotes)
    }
}



