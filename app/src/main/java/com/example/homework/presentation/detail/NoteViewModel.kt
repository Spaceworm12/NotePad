package com.example.homework.presentation.detail

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework.data.models.model.app.AppNotes
import com.example.homework.data.models.model.noterepository.Repository
import com.example.homework.data.models.model.noterepository.RepositoryImplement
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
                    //Загрузка
                    Resource.Loading -> {}
                    is Resource.Data -> viewState = viewState.copy(exit = true)
                    is Resource.Error -> viewState = viewState.copy(
                        errorText = result.error.message ?: ""
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
                    //У тебя явно объявляется запуск Loading в блоке .startWith(Resource.Loading),
                    // можно было и загрузку обработать по человечески
                    Resource.Loading -> {}
                    is Resource.Data -> viewState = viewState.copy(exit = true)
                    is Resource.Error -> viewState = viewState.copy(
                        errorText = result.error.message ?: ""
                    )
                }
            }
            .addTo(disposables)
    }

    override fun onCleared() {
        disposables.clear()
    }

}

