package ru.x5.core_compose.ui.theme.ui.datepicker

import NotesTheme
import android.content.res.Configuration
import android.text.format.DateFormat
import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.homework.presentation.composefutures.ThemeSettings
import com.example.homework.presentation.composefutures.buttons.PrimaryBtn
import java.util.*

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
                .size(width = 400.dp, height = 600.dp)
                .wrapContentSize()
                .background(
                    color = NotesTheme.colors.primary,
                    shape = NotesTheme.shapes.medium
                )
        ) {
            Column(
                Modifier
                    .defaultMinSize(minHeight = NotesTheme.dimens.contentMargin)
                    .fillMaxWidth()
                    .background(
                        color = NotesTheme.colors.secondary,
                        shape = RoundedCornerShape(
                            topStart = NotesTheme.dimens.halfContentMargin,
                            topEnd = NotesTheme.dimens.halfContentMargin
                        )
                    )
                    .padding(NotesTheme.dimens.sideMargin)
            ) {
                Text(
                    text = stringResource(com.example.homework.R.string.select_date),
                    style = NotesTheme.typography.caption,
                    color = Color.White
                )

                Spacer(modifier = Modifier.size(NotesTheme.dimens.inputsMargin))

                Text(
                    text = DateFormat.format("dd.MM.yyyy", currentSelectedDate.value).toString(),
                    style = NotesTheme.typography.h4,
                    color = Color.White
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp),
                contentAlignment = Alignment.Center
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
                modifier = Modifier.wrapContentWidth()
                    .padding(
                        bottom = NotesTheme.dimens.sideMargin,
                        end = NotesTheme.dimens.sideMargin
                    ), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                PrimaryBtn(modifier = Modifier.wrapContentWidth(),text = stringResource(com.example.homework.R.string.cancel),) {
                    onDismissRequest.invoke()
                }

                PrimaryBtn(modifier = Modifier.wrapContentWidth(),text = stringResource(com.example.homework.R.string.yes)) {
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
    // Добавить view в Compose
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

