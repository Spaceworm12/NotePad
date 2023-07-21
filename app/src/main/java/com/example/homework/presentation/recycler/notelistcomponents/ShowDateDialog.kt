package com.example.homework.presentation.recycler.notelistcomponents

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.homework.presentation.composefutures.ThemeSettings
import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.model.NoteType
import com.example.homework.presentation.recycler.ListEvents
import com.example.homework.util.getCurrentDateTime
import com.example.homework.presentation.composefutures.dialogs.DatePickerCalendar
import java.text.DateFormat

@Composable
internal fun ShowDateDialog(note: NoteModel,onDismissInvoke:()->Unit, onUiEvent: (ListEvents) -> Unit) {
    DatePickerCalendar(
        selectedDate = getCurrentDateTime(),
        onDateSelected = {
            note.date =
                DateFormat.getDateInstance(DateFormat.SHORT).format(it)

            onUiEvent.invoke(ListEvents.SaveUserDate(note = note))
            onUiEvent.invoke(ListEvents.ShowCalendar(false, note))
            onUiEvent.invoke(ListEvents.ShowChangeDialog(false, note))
        }
    ) {onDismissInvoke.invoke()}
}

@Preview(name = "ShowDateDialog", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ShowDateDialogPreview() {
    ThemeSettings(themeCode = 1) {
        val note = NoteModel(
            id = 0,
            name = "Заметка",
            description = "Ты собака, я собака, ты собака, я собака, ты собака, я собака, ты собака, " +
                    "я собака, ты собака, я собака, ты собака, я собака, ты собака, я собака",
            type = NoteType.BIRTHDAY_TYPE,
            date = "25.01.22"
        )
        ShowDateDialog(note = note, onUiEvent = {}, onDismissInvoke = {})
    }
}
