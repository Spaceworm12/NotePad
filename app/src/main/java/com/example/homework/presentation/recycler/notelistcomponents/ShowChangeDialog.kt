package com.example.homework.presentation.recycler.notelistcomponents

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.homework.R
import com.example.homework.presentation.composefutures.ThemeSettings
import com.example.homework.presentation.composefutures.dialogs.ItemsDialog
import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.model.NoteType
import com.example.homework.presentation.recycler.ListEvents


@Composable
internal fun ShowChangeDialog(
    note: NoteModel,
    goToTheNextScreen: (NoteModel) -> Unit,
    dismiss: () -> Unit,
    onUiEvent: (ListEvents) -> Unit
) {
    val items = arrayOf(
        stringResource(R.string.open),
        stringResource(R.string.delete),
        stringResource(R.string.change_date)
    )
    val itemsNoDate = arrayOf(stringResource(R.string.open), stringResource(R.string.delete))
    ItemsDialog(
        title = stringResource(R.string.choose_action),
        items = when (note.type) {
            NoteType.BIRTHDAY_TYPE -> items
            NoteType.NOTE_TYPE -> itemsNoDate
        },
        onItemClick = { position ->
            when (position) {
                0 -> {
                    goToTheNextScreen(note)
                    onUiEvent.invoke(ListEvents.ShowChangeDialog(false, note))
                }

                1 -> {
                    onUiEvent.invoke(ListEvents.DeleteNoteModel(note))
                    onUiEvent.invoke(ListEvents.ShowChangeDialog(false, note))
                }

                2 -> {
                    onUiEvent.invoke(ListEvents.ShowCalendar(true, note))
                }
            }
        }
    ) { dismiss.invoke() }
}


@Preview(name = "ShowChangeDialog", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ShowChangeDialogPreview() {
    ThemeSettings {
        ShowChangeDialog(
            note = NoteModel(123, "", "", NoteType.BIRTHDAY_TYPE, ""),
            goToTheNextScreen = {},
            dismiss = {},
            onUiEvent = {})
    }
}
