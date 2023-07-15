package com.example.homework.presentation.composefutures.dialogs

import NotesTheme
import android.app.DatePickerDialog
import android.content.res.Configuration
import android.graphics.Paint.Style
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.homework.R
import com.example.homework.presentation.composefutures.ThemeSettings
import com.example.homework.presentation.composefutures.buttons.DialogBtn
import com.example.homework.presentation.composefutures.buttons.HorizontalBtn
import com.example.homework.presentation.model.NoteModel
import java.util.*


@Composable
fun DateAddDialog(
    note: NoteModel? = null,
    noteDate: String? = null,
    message: String = "",
    negativeButtonText: String = "",
    positiveButtonText: String = "",
    negativeButtonColor: Color? = null,
    positiveButtonColor: Color? = null,
    isEnabled: Boolean = true,
    onPositiveClick: (String) -> Unit,
    onNegativeClick: (() -> Unit)? = null,
    dismiss: () -> Unit,

    ) {
    val noteDate = remember {
        mutableStateOf(note!!.date)
    }
    val selectedDate = remember { mutableStateOf("") }
    Dialog(
        onDismissRequest = { dismiss.invoke() },
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = true)
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = NotesTheme.colors.primary,
                    shape = RoundedCornerShape(NotesTheme.dimens.sideMargin)
                )
                .padding(
                    end = NotesTheme.dimens.sideMargin,
                    start = NotesTheme.dimens.sideMargin,
                    top = NotesTheme.dimens.sideMargin
                )
        ) {
            val mContext = LocalContext.current
            val mYear: Int
            val mMonth: Int
            val mDay: Int
            val mCalendar = Calendar.getInstance()
            mYear = mCalendar.get(Calendar.YEAR)
            mMonth = mCalendar.get(Calendar.MONTH)
            mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
            mCalendar.time = Date()
            val choicedDate: String
            val mDatePickerDialog = DatePickerDialog(
                mContext,
                { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                    selectedDate.value = "$mDayOfMonth.${mMonth + 1}.$mYear"
                }, mYear, mMonth, mDay
            )
            HorizontalBtn(
                modifier = Modifier.fillMaxWidth(),
                text = selectedDate.value,
                isEnabled = true,
                onClick = {
                    mDatePickerDialog.show()
                }
            )

            if (message.isNotBlank()) Text(
                text = message,
                style = NotesTheme.typography.body1,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = NotesTheme.dimens.contentMargin),
                horizontalArrangement = Arrangement.End,
            ) {
                DialogBtn(
                    modifier = Modifier.padding(end = NotesTheme.dimens.sideMargin),
                    text = negativeButtonText.ifBlank { stringResource(R.string.dismiss) },
                    isEnabled = isEnabled,
                    onClick = { if (onNegativeClick != null) onNegativeClick.invoke() else dismiss.invoke() },
                    color = negativeButtonColor ?: NotesTheme.colors.secondary
                )
                DialogBtn(
                    text = positiveButtonText.ifBlank { stringResource(R.string.save) },
                    isEnabled = isEnabled,
                    onClick = { onPositiveClick.invoke() },
                    color = positiveButtonColor ?: NotesTheme.colors.secondary
                )
            }
        }
    }
}

private fun <R> Function<R>.invoke(): R {
    TODO("Not yet implemented")
}


@Preview(name = "DateAddDialog", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun DateAddDialogPreview() {
    ThemeSettings {
        DateAddDialog(
            onPositiveClick = {}) {}
    }
}