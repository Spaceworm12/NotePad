package com.example.homework.presentation.detail

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homework.data.models.model.app.AppNotes
import com.example.homework.data.models.model.noterepository.Repository
import com.example.homework.data.models.model.noterepository.RepositoryImplement
import com.example.homework.presentation.composefutures.FIRST_THEME
import com.example.homework.presentation.composefutures.THEME_CODE
import com.example.homework.presentation.model.Mapper
import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.model.NoteType
import com.example.homework.util.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo


class NoteViewModel(
    private val repo: Repository = RepositoryImplement(AppNotes.dao(), AppNotes.getDb())
) : ViewModel() {
    private val disposables = CompositeDisposable()
    val noteExample = MutableLiveData<NoteModel>()
    private val loading = MutableLiveData<Boolean>()
    val currentTheme = MutableLiveData(FIRST_THEME)
    val exit = MutableLiveData(false)
    private val errorText = MutableLiveData<String>()

    init {
        currentTheme.postValue(AppNotes.getSettingsTheme().getInt(THEME_CODE, FIRST_THEME))
    }

    fun submitUIEvent(event: NoteEvent) {
        handleUIEvent(event)
    }

    private fun handleUIEvent(event: NoteEvent) {
        eventsActions(event)
    }

    private fun eventsActions(event: NoteEvent) {
        when (event) {
            is NoteEvent.SaveNote -> saveNewNote(id = event.id)
            is NoteEvent.SetNote -> noteExample.postValue(event.note)
        }
    }


    @SuppressLint("CheckResult")
    private fun saveNewNote(id: Long) {
        repo.create(Mapper.transformToData(noteExample.value!!.copy(id=id)))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when (result) {
                    Resource.Loading -> {}
                    is Resource.Data -> exit.postValue(true)
                    is Resource.Error -> errorText.postValue(result.error.message ?: "")
                }
            }
            .addTo(disposables)
    }

    private fun deleteNote(id: Long) {
        repo.delete(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when (result) {
                    is Resource.Loading -> loading.postValue(true)
                    is Resource.Data -> { exit.postValue(true)
                    loading.postValue(false)}
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

