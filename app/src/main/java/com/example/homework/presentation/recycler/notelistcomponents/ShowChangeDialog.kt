package com.example.homework.presentation.recycler.notelistcomponents

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
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
internal fun ShowChangeDialog(context: Context, note: NoteModel,goToTheNextScreen: (NoteModel)->Unit, onUiEvent: (ListEvents) -> Unit) {
    val items = arrayOf(stringResource(R.string.open), stringResource(R.string.delete), stringResource(R.string.change_date))
    ItemsDialog(
        title = stringResource(R.string.choose_action),
        items = items,
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
                    if (note.type == NoteType.BIRTHDAY_TYPE) {
                        onUiEvent.invoke(ListEvents.ShowCalendar(true, note))
                    } else {
                        Toast.makeText(context, "Cant", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    ){}


}

@Preview(name = "ShowChangeDialog", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ShowChangeDialogPreview() {
    ThemeSettings {
    }
}
