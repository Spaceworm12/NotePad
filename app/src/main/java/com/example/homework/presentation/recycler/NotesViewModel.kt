package com.example.homework.presentation.recycler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework.data.models.model.app.DbNotes
import com.example.homework.data.models.model.noteRepository.Repository
import com.example.homework.data.models.model.noteRepository.RepositoryImplement
import com.example.homework.presentation.model.Mapper
import com.example.homework.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class NotesViewModel(
    private val repo: Repository = RepositoryImplement(DbNotes.dao(),DbNotes.getDb())
) : ViewModel() {
    private val _viewState = MutableLiveData(NotesViewState())
    val viewStateObs: LiveData<NotesViewState> get() = _viewState
    var viewState: NotesViewState
        get() = _viewState.value!!
        set(value) {
            _viewState.value = value
        }

    fun submitUIEvent(event: NotesEvents) {
        handleUIEvent(event)
    }

    private fun handleUIEvent(event: NotesEvents) {
        when (event) {
            is NotesEvents.GetNotes -> getListNotes()
            is NotesEvents.DeleteNote -> deleteNote(id = event.id)
            is NotesEvents.DeleteAll -> deleteAll()
        }
    }

    private fun getListNotes() {
        viewModelScope.launch {
            viewState = viewState.copy(isLoading = true)
            val result = repo.getAll()
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
            }
        }
    }

    private fun deleteNote(id: Long) {
        viewModelScope.launch {
            viewState = viewState.copy(isLoading = true)
            when (val result = repo.delete(id)) {
                is Resource.Success -> {
                    getListNotes()
                }

                is Resource.Error -> {
                    viewState = viewState.copy(isLoading = false, errorText = result.message ?: "")
                }
            }
        }
    }
    private fun deleteAll() {
        viewModelScope.launch {
            viewState = viewState.copy(isLoading = true)
            when (val result = repo.deleteAll()) {
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




