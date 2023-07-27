package com.example.homework.presentation.composefutures.dialogs

import NotesTheme
import android.content.res.Configuration
import android.text.format.DateFormat
import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.homework.R.string
import com.example.homework.presentation.composefutures.ThemeSettings
import com.example.homework.presentation.composefutures.buttons.PrimaryBtn
import java.util.Calendar
import java.util.Date

/**
 * @param minDate Минимальная дата, которую можно выбрать.
 * @param maxDate Максимальная дата, которую можно выбрать.
 * @param onDateSelected Коллбек выбранной даты.
 * @param onDismissRequest Коллбек закрытия диалога.
 */
@Composable
fun DatePickerCalendar(
    selectedDate: Date,
    minDate: Long? = null,
    maxDate: Long? = null,
    onDateSelected: (Date) -> Unit,
    onDismissRequest: () -> Unit
) {
    val currentSelectedDate = rememberSaveable { mutableStateOf(selectedDate) }

    Dialog(onDismissRequest = { onDismissRequest() }, properties = DialogProperties()) {
        Column(
            modifier = Modifier
                .size(width = 500.dp, height = 650.dp)
                .wrapContentSize()
                .background(
                    color = NotesTheme.colors.primary,
                    shape = RoundedCornerShape(size = NotesTheme.dimens.inputsMargin)
                )
        ) {
            Column(
                Modifier
                    .defaultMinSize(minHeight = NotesTheme.dimens.sideMargin)
                    .fillMaxWidth()
                    .background(
                        color = NotesTheme.colors.secondary,
                        shape = RoundedCornerShape(
                            topStart = NotesTheme.dimens.sideMargin,
                            topEnd = NotesTheme.dimens.sideMargin
                        )
                    )
                    .padding(NotesTheme.dimens.sideMargin)
            ) {
                Text(
                    text = stringResource(id = string.select_date),
                    style = NotesTheme.typography.caption,
                    color = Color.White
                )

                Spacer(modifier = Modifier.size(NotesTheme.dimens.halfContentMargin))

                Text(
                    text = DateFormat.format("dd.MM.yyyy", currentSelectedDate.value).toString(),
                    style = NotesTheme.typography.h4,
                    color = Color.White
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 500.dp), contentAlignment = Alignment.Center
            ) {
                CustomCalendarView(
                    currentSelectedDate.value,
                    minDate,
                    maxDate,
                    onDateSelected = {
                        currentSelectedDate.value = it
                    }
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(
                        bottom = NotesTheme.dimens.sideMargin,
                        top = NotesTheme.dimens.sideMargin,
                        start = NotesTheme.dimens.halfContentMargin,
                        end = NotesTheme.dimens.halfContentMargin,
                    )
            ) {
                PrimaryBtn(
                    modifier = Modifier.weight(1f).padding(end = NotesTheme.dimens.halfContentMargin),
                    text = stringResource(id = string.cancel)
                ) {
                    onDismissRequest.invoke()
                }
                Spacer(modifier = Modifier.size(NotesTheme.dimens.halfContentMargin))

                PrimaryBtn(
                    modifier = Modifier.weight(1f).padding(start = NotesTheme.dimens.halfContentMargin),
                    text = stringResource(id = string.save)
                ) {
                    val newDate = currentSelectedDate.value
                    onDateSelected(
                        Date(
                            maxOf(
                                minOf(maxDate ?: Long.MAX_VALUE, newDate.time),
                                minDate ?: Long.MIN_VALUE
                            )
                        )
                    )
                    onDismissRequest.invoke()
                }
            }
        }
    }
}

/**
 * Используется в [DatePickerCalendar] для создания календаря
 * @param minDate Минимальная дата, которую можно выбрать.
 * @param maxDate Максимальная дата, которую можно выбрать.
 * @param onDateSelected Коллбек выбранной даты.
 */
@Composable
private fun CustomCalendarView(
    selectedDate: Date,
    minDate: Long? = null,
    maxDate: Long? = null,
    onDateSelected: (Date) -> Unit
) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context -> CalendarView(context) },
        update = { view ->
            view.date = selectedDate.time
            if (minDate != null)
                view.minDate = minDate
            if (maxDate != null)
                view.maxDate = maxDate

            view.setOnDateChangeListener { _, year, month, dayOfMonth ->
                onDateSelected(
                    Calendar
                        .getInstance()
                        .apply {
                            set(year, month, dayOfMonth)
                        }
                        .time
                )
            }
        }
    )
}

@Preview(name = "DatePicker", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun DatePickerPreview() {
    ThemeSettings() {
        DatePickerCalendar(
            selectedDate = Calendar.getInstance().time,
            onDateSelected = {},
            onDismissRequest = {})
    }
}

@Preview(device = Devices.PIXEL_C, widthDp = 800, heightDp = 1280)
@Composable
fun DatePickerTabletPreview() {
    ThemeSettings() {
        DatePickerCalendar(
            selectedDate = Calendar.getInstance().time,
            onDateSelected = {},
            onDismissRequest = {})
    }
}

@Preview(device = Devices.PIXEL_C, widthDp = 1280, heightDp = 800)
@Composable
fun DatePickerLandPreview() {
    ThemeSettings() {
        DatePickerCalendar(
            selectedDate = Calendar.getInstance().time,
            onDateSelected = {},
            onDismissRequest = {})
    }
}
