package com.example.homework.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework.data.models.model.app.ApplicationDb
import com.example.homework.data.models.model.noteRepository.Repo
import com.example.homework.data.models.model.noteRepository.RepoImpl
import com.example.homework.presentation.model.Mapper
import com.example.homework.presentation.model.NoteModel
import com.example.homework.util.Resource
import kotlinx.coroutines.launch


class NoteViewModel(
    private val repo: Repo = RepoImpl(ApplicationDb.dao())
) : ViewModel() {
    private val userTitle = MutableLiveData<String>()
    private val userDescription = MutableLiveData<String>()
    val exit = MutableLiveData(false)

    fun submitUIEvent(event: NoteEvent) {
        handleUIEvent(event)
    }

    private fun handleUIEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.SaveUserTitle -> userTitle.postValue(event.text)
            is NoteEvent.SaveUserDescription -> userDescription.postValue(event.text)
            is NoteEvent.SaveNote -> saveNewNote(id = event.id)
            is NoteEvent.DeleteNote -> deleteNote(id = event.id)
        }
    }

    private fun saveNewNote(id: Long) {

        viewModelScope.launch {
            val result = repo.create(
                Mapper.transformToData(
                    NoteModel(
                        id = id,
                        name = userTitle.value ?: "Empty title",
                        description = userDescription.value ?: "Empty Description"
                    )
                )
            )

            when (result) {
                is Resource.Success -> {
                    exit.postValue(true)
                }

                is Resource.Error -> {
                }
            }
        }
    }

    private fun deleteNote(id: Long) {
        viewModelScope.launch {
            val result =
                repo.delete(id)
            when (result) {
                is Resource.Success -> {
                    exit.postValue(true)
                }
                is Resource.Error -> {
                }
            }
        }
    }
}
