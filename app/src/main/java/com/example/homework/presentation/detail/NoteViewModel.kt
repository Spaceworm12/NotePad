package com.example.homework.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework.data.models.model.app.DbNotes
import com.example.homework.data.models.model.noteRepository.Repository
import com.example.homework.data.models.model.noteRepository.RepositoryImplement
import com.example.homework.presentation.model.Mapper
import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.model.NoteType
import com.example.homework.util.Resource
import kotlinx.coroutines.launch


class NoteViewModel(
    private val repo: Repository = RepositoryImplement(DbNotes.dao(), DbNotes.getDb())
) : ViewModel() {
    private val _viewState = MutableLiveData(NoteViewState())
    val viewStateObs: LiveData<NoteViewState> get() = _viewState
    var viewState: NoteViewState
        get() = _viewState.value!!
        set(value) {
            _viewState.value = value
        }
    fun submitUIEvent(event: NoteEvent) {
        handleUIEvent(event)
    }

    private fun handleUIEvent(event: NoteEvent) {
        eventsAction(event)
    }

    private fun eventsAction(event: NoteEvent) {
        eventsActions(event)
    }

    private fun eventsActions(event: NoteEvent) {
        when (event) {
            is NoteEvent.SaveUserTitle -> viewState = viewState.copy(userTitle = event.text)
            is NoteEvent.SaveUserDescription -> viewState =
                viewState.copy(userDescription = event.text)
            is NoteEvent.SaveUserDate -> viewState = viewState.copy(userDate = event.text)
            is NoteEvent.SaveNote -> saveNewNote(id = event.id)
            is NoteEvent.DeleteNote -> deleteNote(id = event.id)
            NoteEvent.Exit -> goBack()
            NoteEvent.Error -> viewState = viewState.copy(errorText = "ERROR")
        }
    }

    private fun goBack() {
        viewModelScope.launch {
            viewState = viewState.copy(exit = true)
        }
    }
    private fun saveNewNote(id: Long) {
        viewModelScope.launch {
            val result = repo.create(
                Mapper.transformToData(
                    NoteModel(
                        id = id,
                        name = viewState.userTitle,
                        description = viewState.userDescription,
                        type = NoteType.NOTE_TYPE,
                        date = viewState.userDate
                    )
                )
            )

            when (result) {
                is Resource.Success -> {
                    viewState=viewState.copy(exit = true)
                }
                is Resource.Error -> {
                    viewState=viewState.copy(errorText = result.message?:"ERROR")
                }
            }
        }
    }
    private fun deleteNote(id: Long) {
        viewModelScope.launch {
            when (val result = repo.delete(id)) {
                is Resource.Success -> {
                    goBack()
                }
                is Resource.Error -> {
                    viewState=viewState.copy(errorText = result.message?:"ERROR")
                }
            }
        }
    }
}

