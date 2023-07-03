package com.example.homework.presentation.detailbd

sealed class BirthdayEvent {
    class SaveUserBirth(val text: String) : BirthdayEvent()
    class SaveBirthDate(val text: String) : BirthdayEvent()
    class SaveBirth(val id: Long) : BirthdayEvent()
    class SaveUserDescription(val text: String) : BirthdayEvent()
    object Exit : BirthdayEvent()
    object Error : BirthdayEvent()

}