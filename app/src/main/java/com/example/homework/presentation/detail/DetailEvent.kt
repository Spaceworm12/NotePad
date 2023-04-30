package com.example.homework.presentation.detail

sealed class DetailEvent {

    class SaveUserText(val text: String): DetailEvent()
}