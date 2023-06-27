package com.example.homework.presentation.SwitchTheme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework.data.models.model.app.DbNotes
import com.example.homework.presentation.detail.NoteEvent
import com.example.homework.util.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

private const val THEME_CODE = "THEME_CODE"

class SelectThemeViewModel() : ViewModel() {

    private val disposables = CompositeDisposable()
    private val _viewState = MutableLiveData(SelectThemeViewState())
    private val theme = MutableLiveData(SelectThemeViewState())
    val errorText = MutableLiveData<String>()
    val viewStateObs: LiveData<SelectThemeViewState> get() = _viewState
    private var viewState: SelectThemeViewState
        get() = _viewState.value!!
        set(value) {
            _viewState.value = value
        }

    fun submitUIEvent(event: SelectThemeEvent) {
        handleUIEvent(event)
    }

    private fun handleUIEvent(event: SelectThemeEvent) {
        setEvents(event)
    }

    private fun setEvents(event: SelectThemeEvent) {
        when (event) {
            is SelectThemeEvent.CheckTheme -> checkTheme()
            is SelectThemeEvent.Exit -> goBack()
        }
    }


    private fun checkTheme() {
        viewState = viewState.copy(isLoading = true)
        val result =
            DbNotes.getSettings().getString(com.example.homework.presentation.recycler.THEME_CODE)
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
    private fun goBack() {
        viewModelScope.launch {
            viewState = viewState.copy
        }
    }
}