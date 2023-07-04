package com.example.homework.presentation.detail

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
import com.example.homework.presentation.model.Mapper
import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.model.NoteType
import com.example.homework.util.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.launch


class NoteViewModel(
    private val repo: Repository = RepositoryImplement(AppNotes.dao(), AppNotes.getDb())
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val noteExample = MutableLiveData<NoteModel>()
    private val loading: Boolean=false
    private val currentTheme = MutableLiveData(FIRST_THEME)
    private val exit = MutableLiveData(false)

    init {
        currentTheme.postValue(AppNotes.getSettingsTheme().getInt(THEME_CODE, FIRST_THEME))
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
            is NoteEvent.SaveNote -> saveNewNote(id=event.id)
            is NoteEvent.SetNote ->noteExample.postValue(event.note)
        }
    }



    @SuppressLint("CheckResult")
    private fun saveNewNote(id: Long) {
        repo.create(
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
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when (result) {
                    Resource.Loading -> viewState = viewState.copy(loading = true)
                    is Resource.Data -> viewState.copy(exit = true, loading = false)
                    is Resource.Error -> viewState.copy(
                        errorText = result.error.message ?: "", loading = false
                    )

                }
            }
            .addTo(disposables)
    }

    private fun deleteNote(id: Long) {
        repo.delete(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when (result) {
                    is Resource.Loading -> viewState = viewState.copy(loading = true)
                    is Resource.Data -> viewState = viewState.copy(exit = true, loading = false)
                    is Resource.Error -> viewState = viewState.copy(
                        errorText = result.error.message ?: "", loading = false
                    )
                }
            }
            .addTo(disposables)
    }

    override fun onCleared() {
        disposables.clear()
    }

}

