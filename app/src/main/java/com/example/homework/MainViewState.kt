package com.example.homework

import java.text.SimpleDateFormat
import java.util.*

data class MainViewState(var currentTime: String = "") {
    init {
        initTime()
    }

    private fun initTime() {
        val endPointTime = getCurrentDateTime().toString("HH:mm:ss")
        currentTime = endPointTime
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }
}