package com.example.homework.presentation.detailBd

sealed class BdEvent {

    class SaveUserBd(val text: String) : BdEvent()

    class SaveDateBd(val text: String) : BdEvent()

    class SaveBd(val id: Long) : BdEvent()

    class SaveUserDescription(val text: String) : BdEvent()

    object Exit : BdEvent()

    object Error : BdEvent()

}