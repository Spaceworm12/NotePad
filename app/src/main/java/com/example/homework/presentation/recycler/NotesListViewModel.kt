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


class NotesListViewModel(
    private val noteRepository: NoteRepository = NoteRepositoryImpl()
) : ViewModel() {
    private val _viewState = MutableLiveData(NotesListViewState())
    val viewStateObs: LiveData<NotesListViewState> get() = _viewState
    private var viewState: NotesListViewState
        get() = _viewState.value!!
        set(value) {
            _viewState.value = value
        }

    fun submitUIEvent(event: NotesListEvent) {
        handleUIEvent(event)
    }

    private fun handleUIEvent(event: NotesListEvent) {
        when (event) {
            NotesListEvent.GetNotes -> getListNotes()
            is NotesListEvent.AddNote -> addNewNote(item = event.note)
            is NotesListEvent.DeleteNote -> deleteNote(note = event.note, index = event.index)
        }
    }
    private fun getListNotes() {
        viewModelScope.launch {
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



