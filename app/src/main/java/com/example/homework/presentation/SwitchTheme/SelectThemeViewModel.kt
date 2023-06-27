package com.example.homework.presentation.SwitchTheme

import android.widget.RadioButton
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework.R
import com.example.homework.data.models.model.app.DbNotes
import com.example.homework.presentation.detail.NoteEvent
import com.example.homework.presentation.detail.NoteViewState
import com.example.homework.util.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

private const val THEME_CODE = "THEME_CODE"

class SelectThemeViewModel() : ViewModel() {
    private val _viewState = MutableLiveData(SelectThemeViewState())
    val viewStateObs: LiveData<NoteViewState> get() = _viewState
    var viewState: NoteViewState
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
            is SelectThemeEvent.Exit -> viewState=viewState.copy(exit = true)
        }
    }

    private fun checkTheme(r:RadioButton) {
        r.isChecked = DbNotes.getSettings().edit().putInt(THEME_CODE, R.style.Theme_Homework).apply()
    }

    }