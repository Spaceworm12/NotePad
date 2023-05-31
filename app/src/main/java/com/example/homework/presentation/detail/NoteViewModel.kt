package com.example.homework.presentation.detail

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework.data.models.model.app.DbNotes
import com.example.homework.data.models.model.noteRepository.Repository
import com.example.homework.data.models.model.noteRepository.RepositoryImplement
import com.example.homework.presentation.model.Mapper
import com.example.homework.presentation.model.NoteModel
import com.example.homework.util.Resource
import kotlinx.coroutines.launch


class NoteViewModel(
    private val repo: Repository = RepositoryImplement(DbNotes.dao(), DbNotes.getDb())
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
            is NoteEvent.Exit -> goBack()

        }
    }

    private fun goBack() {
        viewModelScope.launch {
            exit.postValue(true)
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

            when (repo.delete(id)) {
                is Resource.Success -> {
                    goBack()

                }
                is Resource.Error -> {//описать
                }
            }
        }
    }
}
