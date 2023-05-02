package com.example.homework.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class DetailViewModelNote: ViewModel() {

        val userText = MutableLiveData<String>()


    //Пример LiveData c LiveData с примитивным типом данных


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
