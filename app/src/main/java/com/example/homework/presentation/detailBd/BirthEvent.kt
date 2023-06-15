package com.example.homework.presentation.detailBd

sealed class BirthEvent {

    class SaveUserBirth(val text: String) : BirthEvent()

    class SaveBirthDate(val text: String) : BirthEvent()

    class SaveBirth(val id: Long) : BirthEvent()

    class SaveUserDescription(val text: String) : BirthEvent()

    object Exit : BirthEvent()

    object Error : BirthEvent()

}