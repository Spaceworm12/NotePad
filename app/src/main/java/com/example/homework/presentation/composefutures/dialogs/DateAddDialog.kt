package com.example.homework.presentation.composefutures.dialogs

import NotesTheme
import android.app.DatePickerDialog
import android.content.res.Configuration
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
import java.util.*


@Composable
fun DateAddDialog(
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

    val startTitle = stringResource(id = R.string.date)
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    calendar.time = Date()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val selectedDate = remember { mutableStateOf(startTitle) }
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            selectedDate.value = "$mDayOfMonth.${mMonth + 1}.$mYear"
        },
        year,
        month,
        day
    )

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

            HorizontalBtn(
                modifier = Modifier.fillMaxWidth(),
                text = selectedDate.value,
                isEnabled = true,
                onClick = { datePickerDialog.show() }
            )

            if (message.isNotBlank())
                Text(
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
                    onClick = { onPositiveClick.invoke(selectedDate.value) },
                    color = positiveButtonColor ?: NotesTheme.colors.secondary
                )
            }
        }
    }
}

@Preview(name = "DateAddDialog", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun DateAddDialogPreview() {
    ThemeSettings {
        DateAddDialog(
            onPositiveClick = {}) {}
    }
}
