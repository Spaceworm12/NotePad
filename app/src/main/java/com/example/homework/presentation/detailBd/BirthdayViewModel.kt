package com.example.homework.presentation.detailBd

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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.launch


class BirthdayViewModel(
    private val repo: Repository = RepositoryImplement(DbNotes.dao(), DbNotes.getDb())
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _viewState = MutableLiveData(BirthdayViewState())
    val viewStateObs: LiveData<BirthdayViewState> get() = _viewState
    var viewState: BirthdayViewState
        get() = _viewState.value!!
        set(value) {
            _viewState.value = value
        }
    fun submitUIEvent(event: BirthdayEvent) {
        handleUIEvent(event)
    }

    private fun handleUIEvent(event: BirthdayEvent) {
        when (event) {
            is BirthdayEvent.SaveUserBirth -> viewState = viewState.copy(user = event.text)
            is BirthdayEvent.SaveBirthDate -> viewState = viewState.copy(date = event.text)
            is BirthdayEvent.SaveBirth -> saveNewBd(id = event.id)
            is BirthdayEvent.SaveUserDescription -> viewState = viewState.copy(description = event.text)
            BirthdayEvent.Exit -> goBack()
            BirthdayEvent.Error -> viewState = viewState.copy(errorText = "ERR")
        }
    }

    private fun goBack() {
        viewModelScope.launch {
           viewState = viewState.copy(exit = true)
        }
    }

    private fun saveNewBd(id: Long) {
            val result = repo.create(
                Mapper.transformToData(
                    NoteModel(
                        id = id,
                        name = viewState.user,
                        description = viewState.description,
                        type = NoteType.BIRTHDAY_TYPE,
                        date = viewState.date
                    )
                )
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
            when (result) {
                is Resource.Loading -> {}
                is Resource.Data -> {
                    viewState = viewState.copy(exit = true)
                }
                is Resource.Error -> {
                   viewState = viewState.copy(errorText = "ERR")
                }
            }
        }
                .addTo(disposables)
    }
    override fun onCleared() {
        disposables.clear()
    }
}


