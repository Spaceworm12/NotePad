package com.example.homework.presentation.composefutures.dialogs

import android.annotation.SuppressLint
import android.widget.CalendarView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.homework.presentation.composefutures.ThemeSettings


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calendar():String {
    var date by remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Выберите дату", textAlign = TextAlign.Center) },
            )
        },
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                AndroidView(factory = { CalendarView(it) }, update = {
                    it.setOnDateChangeListener { calendar, year, month, day ->
                        date = "$day - ${month + 1} - $year"
                    }
                })
                Text(text = date)
            }
        })
    return date
}


@Preview
@Composable
private fun ShowCalendar() {
    ThemeSettings {
        Calendar()
    }
}