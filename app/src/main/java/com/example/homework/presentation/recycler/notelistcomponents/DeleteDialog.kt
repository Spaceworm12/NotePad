package com.example.homework.presentation.recycler.notelistcomponents

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.homework.R
import com.example.homework.presentation.composefutures.ThemeSettings
import com.example.homework.presentation.composefutures.dialogs.DefaultDialog
import com.example.homework.presentation.recycler.ListEvents


@Composable
internal fun DeleteDialog(id: Long, onUiEvent: (ListEvents) -> Unit) {
    DefaultDialog(
        title = stringResource(id = R.string.delete_question),
        onPositiveClick = {
            if (id != -1L) onUiEvent.invoke(ListEvents.DeleteNote(id))
            onUiEvent.invoke(ListEvents.ShowDeleteDialog(false, -1))
        },
        onNegativeClick = { onUiEvent.invoke(ListEvents.ShowDeleteDialog(false, -1)) }) {
        onUiEvent.invoke(ListEvents.ShowDeleteDialog(false, -1))
    }
}

@Preview(name = "DeleteDialog", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun DeleteDialogPreview() {
    ThemeSettings {
        DeleteDialog(0) {}
    }
}
