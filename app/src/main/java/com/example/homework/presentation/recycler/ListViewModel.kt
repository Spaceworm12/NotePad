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
import kotlinx.coroutines.launch


class ListViewModel(
    private val repo: Repository = RepositoryImplement(DbNotes.dao(), DbNotes.getDb())
) : ViewModel() {
    private val _viewState = MutableLiveData(ListViewState())
    val errorText = MutableLiveData<String>()
    val viewStateObs: LiveData<ListViewState> get() = _viewState
    var viewState: ListViewState
        get() = _viewState.value!!
        set(value) {
            _viewState.value = value
        }

    fun submitUIEvent(event: ListEvents) {
        handleUIEvent(event)
    }

    private fun handleUIEvent(event: ListEvents) {
        setEvents(event)
    }

    private fun setEvents(event: ListEvents) {
        when (event) {
            is ListEvents.GetNotes -> getListNotes()
            is ListEvents.DeleteNote -> deleteNote(id = event.id)
            is ListEvents.DeleteAll -> deleteAll()
            is ListEvents.SaveUserDate -> changeDate(date = event.date, id = event.id)
        }
    }

    private fun getListNotes() {
        viewModelScope.launch {
            viewState = viewState.copy(isLoading = true)
            when (val result = repo.getAll()) {
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

    private fun changeDate(date: String, id: Long) {

        viewModelScope.launch {
            val result = repo.changeDate(
                date, id
            )
            when (result) {
                is Resource.Success -> {
                    getListNotes()
                }
                is Resource.Error -> {
                    errorText.postValue(result.message ?: "CAN NOTE ADD DATE")
                }
            }
        }
    }
}





