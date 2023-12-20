package com.example.homework

import android.content.SharedPreferences
import com.example.homework.util.getCurrentDateTimeAsString

data class MainViewState(
    var currentTime: String = ""
) {

    val correctCurrentTime = currentTime.ifBlank { getCurrentDateTimeAsString() }



}
