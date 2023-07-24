package com.example.homework.presentation.recycler.notelistcomponents

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.homework.R
import com.example.homework.presentation.composefutures.ThemeSettings
import com.example.homework.presentation.composefutures.dialogs.DefaultDialog
import com.example.homework.presentation.recycler.ListEvents

@Composable
internal fun ClearAllNotes(
    context: Context,
    onDismiss: () -> Unit,
    onUiEvent: (ListEvents) -> Unit
) {
    DefaultDialog(
        title = stringResource(id = R.string.delete_all_question),
        negativeButtonText = stringResource(id = R.string.cancel),
        positiveButtonText = stringResource(id = R.string.yes),
        onPositiveClick = {
            onUiEvent.invoke(ListEvents.DeleteAll)
            Toast.makeText(context, R.string.all_notes_delete, Toast.LENGTH_SHORT)
                .show()
            onUiEvent.invoke(ListEvents.ClearAll(false))
        },
        onNegativeClick = {
            onUiEvent.invoke(ListEvents.ClearAll(false))
        })
    { onDismiss.invoke() }
}

@Preview(name = "DeleteDialog", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ClearAllNotesPreview() {
    ThemeSettings {
        {}
    }
}
