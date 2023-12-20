package com.example.homework

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homework.util.getCurrentDateTimeAsString
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _viewState = MutableLiveData(MainViewState())
    val viewStateObs: LiveData<MainViewState> get() = _viewState
    var viewState: MainViewState
        get() = _viewState.value!!
        set(value) {
            _viewState.value = value
        }

    init {
        startTimeDetection()
    }

    private fun startTimeDetection() {
        Observable.interval(5000L, TimeUnit.MILLISECONDS)
            .timeInterval()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { showTime() }
            .addTo(disposables)
    }

    private fun showTime() {
        val time = getCurrentDateTimeAsString()
        viewState = viewState.copy(currentTime = time)
    }

    override fun onCleared() {
        disposables.clear()
    }
}
