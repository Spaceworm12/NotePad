package com.example.homework.presentation.detailbd

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework.data.models.model.app.AppNotes
import com.example.homework.data.models.model.noterepository.Repository
import com.example.homework.data.models.model.noterepository.RepositoryImplement
import com.example.homework.presentation.composefutures.FIRST_THEME
import com.example.homework.presentation.composefutures.THEME_CODE
import com.example.homework.presentation.detail.NoteEvent
import com.example.homework.presentation.model.Mapper
import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.model.NoteType
import com.example.homework.util.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.launch


class BirthdayViewModel(
    private val repo: Repository = RepositoryImplement(AppNotes.dao(), AppNotes.getDb())
) : ViewModel() {
    private val disposables = CompositeDisposable()
    val bdNoteExample = MutableLiveData<NoteModel>()
    private val loading = MutableLiveData<Boolean>()
    val currentTheme = MutableLiveData(FIRST_THEME)
    val exit = MutableLiveData(false)
    private val errorText = MutableLiveData<String>()

    init {
        currentTheme.postValue(AppNotes.getSettingsTheme().getInt(THEME_CODE, FIRST_THEME))
    }

    fun submitUIEvent(event: BirthdayEvent) {
        handleUIEvent(event)
    }

    private fun handleUIEvent(event: BirthdayEvent) {
        eventsActions(event)
    }

    private fun eventsActions(event: BirthdayEvent) {
        when (event) {
            is BirthdayEvent.SaveBirthdayNote -> saveNewBd(id = event.id)
            is BirthdayEvent.SetBirthdayNote -> bdNoteExample.postValue(event.note)
        }
    }

    private fun saveNewBd(id: Long) {
        repo.create(Mapper.transformToData(bdNoteExample.value!!.copy(id = id)))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when (result) {
                    Resource.Loading -> loading.postValue(true)
                    is Resource.Data -> exit.postValue(true)
                    is Resource.Error -> errorText.postValue(result.error.message ?: "")
                }
            }
            .addTo(disposables)
    }


    private fun deleteBdNote(id: Long) {
        repo.delete(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when (result) {
                    is Resource.Loading -> loading.postValue(true)
                    is Resource.Data -> {
                        exit.postValue(true)
                        loading.postValue(false)
                    }
                    is Resource.Error -> {
                        errorText.postValue(result.error.message ?: "")
                        loading.postValue(false)
                    }
                }
            }
            .addTo(disposables)
    }


    override fun onCleared() {
        disposables.clear()
    }
}

//    private fun goBack() {
//        viewModelScope.launch {
//           viewState = viewState.copy(exit = true)
//        }





