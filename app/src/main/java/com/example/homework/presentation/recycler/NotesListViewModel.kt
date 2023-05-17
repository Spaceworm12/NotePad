package com.example.homework.presentation.recycler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework.data.models.model.app.App
import com.example.homework.data.models.model.noteRepository.NoteRepository
import com.example.homework.data.models.model.noteRepository.NoteRepositoryImpl
import com.example.homework.presentation.model.Mapper
import com.example.homework.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class NotesListViewModel(
    private val noteRepository: NoteRepository = NoteRepositoryImpl(App.getExampleDao())
) : ViewModel() {
    private val _viewState = MutableLiveData(NotesListViewState())
    val viewStateObs: LiveData<NotesListViewState> get() = _viewState
    var viewState: NotesListViewState
        get() = _viewState.value!!
        set(value) {
            _viewState.value = value
        }

    fun submitUIEvent(event: NotesListEvent) {
        handleUIEvent(event)
    }

    private fun handleUIEvent(event: NotesListEvent) {
        when (event) {
            is NotesListEvent.GetNotes -> getListNotes()
            is NotesListEvent.DeleteNote -> deleteNote(id = event.id)
            is NotesListEvent.DeleteAll -> getListNotes()
        }
    }

    private fun getListNotes() {
        viewModelScope.launch {
            viewState = viewState.copy(isLoading = true)
            val result = noteRepository.getItems()
            delay(1500)
            when (result) {
                is Resource.Success -> {
                    viewState = viewState.copy(
                        notesList = Mapper.transformToPresentation(result.data ?: emptyList()),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    viewState =
                        viewState.copy(isLoading = false, errorText = result.message ?: "error")
                }

                else -> {}
            }
        }
    }

    private fun deleteNote(id: Long) {
        viewModelScope.launch {
            viewState = viewState.copy(isLoading = true)
            val result = noteRepository.deleteExample(id)
            when (result) {
                is Resource.Success -> {
                    getListNotes()
                }

                is Resource.Error -> {
                    viewState = viewState.copy(isLoading = false, errorText = result.message ?: "")
                }
            }
        }
    }

    }



