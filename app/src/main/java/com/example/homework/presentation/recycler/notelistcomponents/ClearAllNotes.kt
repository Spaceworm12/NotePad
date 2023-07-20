package com.example.homework.presentation.recycler.notelistcomponents

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.homework.R
import com.example.homework.presentation.composefutures.ThemeSettings
import com.example.homework.presentation.composefutures.dialogs.DefaultDialog
import com.example.homework.presentation.recycler.ListEvents

@Composable
private fun ClearAllNotes(context:Context,onUiEvent:(ListEvents) -> Unit) {
    DefaultDialog(
        title = stringResource(id = R.string.delete_all_question),
        negativeButtonText = stringResource(id = R.string.cancel),
        positiveButtonText = stringResource(id = R.string.yes),
        onPositiveClick = {
            onUiEvent.invoke(ListEvents.DeleteAll)
            Toast.makeText(context, R.string.all_notes_delete, Toast.LENGTH_SHORT)
                .show()
            viewModel.submitUIEvent(ListEvents.ClearAll(false))
        },
        onNegativeClick = {
            viewModel.submitUIEvent(ListEvents.ClearAll(false))
        })
    {}
}

@Preview(name = "DeleteDialog", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun DeleteDialogPreview() {
    ThemeSettings {
        DeleteDialog(0) {}
    }
}
