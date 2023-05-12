package com.example.homework.presentation.detail

sealed class DetailEvent {

    class SaveUserTitle(val text: String) : DetailEvent()

    class SaveUserDescription(val text: String) : DetailEvent()

    class SaveNote(val id: Long) : DetailEvent()
}