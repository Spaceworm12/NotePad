package com.example.homework

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

data class MainViewState(val time:String = s){

    private fun initTime(s:String): String {
        if(s==""){
            s==getCurrentDateTime().toString()
        }else{
            s==""
        }
        return s
    }


    private fun checkTime(time:String) {
        val current
        = getCurrentDateTime().toString("HH:mm:ss")
    }
    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }
}