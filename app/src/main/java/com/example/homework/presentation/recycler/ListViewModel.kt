package com.example.homework.presentation.recycler

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homework.data.models.model.app.DbNotes
import com.example.homework.data.models.model.noteRepository.Repository
import com.example.homework.data.models.model.noteRepository.RepositoryImplement
import com.example.homework.presentation.model.Mapper
import com.example.homework.util.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

private const val THEME_CODE = "THEME_CODE"

class ListViewModel(
    private val repo: Repository = RepositoryImplement(DbNotes.dao(), DbNotes.getDb())
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _viewState = MutableLiveData(ListViewState())
    private val theme = MutableLiveData(ListViewState())
    // private
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
            is ListEvents.CheckTheme -> checkTheme()
        }
    }
    private fun checkTheme() {
        viewState = viewState.copy(isLoading = true)
        val result = DbNotes.getSettings().getString(THEME_CODE, )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when (result) {
                    is Resource.Loading -> {}
                    is Resource.Data -> {
                        getListNotes()
                    }
                    is Resource.Error -> {
                        viewState = viewState.copy(isLoading = false, errorText = "err")
                    }
                }
            }
    }

    private fun getListNotes() {
        viewState = viewState.copy(isLoading = true)
        //Не используется запись в переменную
        val result = repo.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when (result) {
                    is Resource.Loading -> {}
                    is Resource.Data -> {
                        viewState = viewState.copy(
                            notesList = Mapper.transformToPresentation(result.data ?: emptyList()),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        viewState =
                            viewState.copy(isLoading = false, errorText = "error")
                    }
                }
            }
            .addTo(disposables)
    }

    private fun deleteNote(id: Long) {
        viewState = viewState.copy(isLoading = true)
        //Не используется запись в переменную
        val result = repo.delete(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when (result) {
                    is Resource.Loading -> {}
                    is Resource.Data -> {
                        getListNotes()
                    }
                    is Resource.Error -> {
                        viewState = viewState.copy(isLoading = false, errorText = "err")
                    }
                }
            }
    }

    private fun deleteAll() {
        viewState = viewState.copy(isLoading = true)
        //Не используется запись в переменную
        val result = repo.deleteAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when (result) {
                    is Resource.Loading -> {}
                    is Resource.Data -> {
                        getListNotes()
                    }
                    is Resource.Error -> {
                        viewState = viewState.copy(isLoading = false, errorText = "err")
                    }
                }
            }
    }

    private fun changeDate(date: String, id: Long) {
        //Не используется запись в переменную
        val result = repo.changeDate(
            date, id
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when (result) {
                    is Resource.Loading -> {}
                    is Resource.Data -> {
                        getListNotes()
                    }
                    is Resource.Error -> {
                        errorText.postValue("CAN NOTE ADD DATE")
                    }
                }
            }
    }

    override fun onCleared() {
        disposables.clear()
    }

}





