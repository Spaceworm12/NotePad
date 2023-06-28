package com.example.homework.presentation.SwitchTheme

import android.widget.RadioButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework.R
import com.example.homework.data.models.model.app.DbNotes
import kotlinx.coroutines.launch

private const val THEME_CODE = "THEME_CODE"
private const val SELECTED_POSITION = "SELECTED_POSITION"

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
            currentTheme = DbNotes.getSettingsTheme().getInt(THEME_CODE, R.style.Theme_Homework)
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
            is SelectThemeEvents.CheckIn -> checkIn(event.theme)
        }
    }

    private fun switchTheme(theme: Int) {
        DbNotes.getSettingsTheme().edit().putInt(THEME_CODE, theme).apply()
    }

    private fun checkIn(btn:RadioButton):Boolean {
        if (btn == R.style.Theme_Homework) {
      return true
        } else if (theme == R.style.Theme_Second) {
           return true
        }
    }

    private fun goBack() {
        viewModelScope.launch {
            viewState = viewState.copy(exit = true)
        }
    }
}

