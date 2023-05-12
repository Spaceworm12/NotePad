package com.example.homework.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework.data.models.model.app.App
import com.example.homework.data.models.model.noteRepository.NoteRepository
import com.example.homework.data.models.model.noteRepository.NoteRepositoryImpl
import com.example.homework.presentation.model.Mapper
import com.example.homework.presentation.model.NoteModel
import com.example.homework.util.Resource
import kotlinx.coroutines.launch


class DetailNoteViewModel(
    private val noteRepository: NoteRepository = NoteRepositoryImpl(App.getExampleDao())
) : ViewModel() {
    private val userTitle = MutableLiveData<String>()
    private val userDescription = MutableLiveData<String>()
    val exit = MutableLiveData(false)

    fun submitUIEvent(event: DetailEvent) {
        handleUIEvent(event)
    }

    private fun handleUIEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.SaveUserTitle -> userTitle.postValue(event.text)
            is DetailEvent.SaveUserDescription -> userDescription.postValue(event.text)
            is DetailEvent.SaveNote -> saveNewNote(id = event.id)
        }
    }

    private fun saveNewNote(id: Long) {

        viewModelScope.launch {
            val result = noteRepository.insertExample(
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

                else -> {}
            }
        }
    }

}
