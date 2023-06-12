package com.example.homework.presentation.detailBd

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


class BdViewModel(
    private val repo: Repository = RepositoryImplement(DbNotes.dao(), DbNotes.getDb())
) : ViewModel() {
    private val user = MutableLiveData<String>()
    private val date = MutableLiveData<String>()
    private val description = MutableLiveData<String>()
    val errorText = MutableLiveData<String>()

    val exit = MutableLiveData(false)

    fun submitUIEvent(event: BdEvent) {
        handleUIEvent(event)
    }

    private fun handleUIEvent(event: BdEvent) {
        when (event) {
            is BdEvent.SaveUserBd -> user.postValue(event.text)
            is BdEvent.SaveDateBd -> date.postValue(event.text)
            is BdEvent.SaveBd -> saveNewBd(id = event.id)
            is BdEvent.SaveUserDescription -> description.postValue(event.text)
            BdEvent.Exit -> goBack()
            BdEvent.Error -> errorText.postValue("")
        }
    }

    private fun goBack() {
        viewModelScope.launch {
            exit.postValue(true)
        }
    }

    private fun saveNewBd(id: Long) {

        viewModelScope.launch {
            val result = repo.create(
                Mapper.transformToData(
                    NoteModel(
                        id = id,
                        name = user.value ?: "Empty user",
                        description = description.value ?: "Empty date",
                        type = NoteType.BIRTHDAY_TYPE,
                        date = date.value ?: "Date"
                    )
                )
            )

            when (result) {
                is Resource.Success -> {
                    exit.postValue(true)

                }

                is Resource.Error -> {
                    errorText.postValue(result.message ?: "")
                }
            }
        }
    }
}


