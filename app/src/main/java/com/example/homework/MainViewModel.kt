package com.example.homework

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*


class MainViewModel : ViewModel() {
    private val _viewState = MutableLiveData(MainViewState())
    val viewStateObs: LiveData<MainViewState> get() = _viewState
    private var viewState: MainViewState
        get() = _viewState.value!!
        set(value) {
            _viewState.value = value
        }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun showTime(state: MainViewState) {
        val date = getCurrentDateTime().toString("HH:mm:ss")
        }
    }
}