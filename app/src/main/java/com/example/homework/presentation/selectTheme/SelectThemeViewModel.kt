package com.example.homework.presentation.selectTheme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework.R
import com.example.homework.data.models.model.app.AppNotes
import kotlinx.coroutines.launch

private const val THEME_CODE = "THEME_CODE"

class SelectThemeViewModel : ViewModel() {
    private val _viewState = MutableLiveData(SelectThemeViewState())
    val viewStateObs: LiveData<SelectThemeViewState> get() = _viewState
    var viewState: SelectThemeViewState
        get() = _viewState.value!!
        set(value) {
            _viewState.value = value
        }

    init {
        viewState = viewState.copy(
            currentTheme = AppNotes.getSettingsTheme().getInt(THEME_CODE, R.style.Theme_Homework)
        )
    }

    fun submitUIEvent(event: SelectThemeEvents) {
        handleUIEvent(event)
    }

    private fun handleUIEvent(event: SelectThemeEvents) {
        setEvents(event)
    }

    private fun setEvents(event: SelectThemeEvents) {
        when (event) {
            is SelectThemeEvents.SwitchTheme -> switchTheme(event.theme)
            is SelectThemeEvents.Exit -> goBack()
        }
    }

    private fun switchTheme(theme: Int) {
        AppNotes.getSettingsTheme().edit().putInt(THEME_CODE, theme).apply()
        viewState = viewState.copy(currentTheme = theme)
    }

    private fun goBack() {
        viewModelScope.launch {
            viewState = viewState.copy(exit = true)

        }
    }
}

