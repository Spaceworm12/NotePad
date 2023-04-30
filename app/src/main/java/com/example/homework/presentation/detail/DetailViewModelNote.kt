package com.example.homework.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class DetailViewModelNote: ViewModel() {

    //Пример LiveData c LiveData с примитивным типом данных
    private val userText = MutableLiveData<String>()

    fun submitUIEvent(event: DetailEvent) {
        handleUIEvent(event)
    }

    private fun handleUIEvent(event: DetailEvent) {
        when (event) {
            //Сохраняем в нашу LiveData
            is DetailEvent.SaveUserText -> userText.postValue(event.text)
        }
    }

}
