package com.example.homework.presentation.recycler.notelistcomponents

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.homework.R
import com.example.homework.presentation.composefutures.FIRST_THEME
import com.example.homework.presentation.composefutures.SECOND_THEME
import com.example.homework.presentation.composefutures.THIRD_THEME
import com.example.homework.presentation.composefutures.ThemeSettings
import com.example.homework.presentation.composefutures.dialogs.ItemsDialog
import com.example.homework.presentation.recycler.ListEvents

@Composable
internal fun ShowSettingsDialog(onUiEvent: (ListEvents) -> Unit) {

    val items = arrayOf(
        stringResource(id = R.string.first_theme),
        stringResource(id = R.string.second_theme),
        stringResource(id = R.string.third_theme),
    )

    ItemsDialog(
        title = stringResource(R.string.set_theme),
        items = items,
        onItemClick = { position ->
            when (position) {
                0 -> onUiEvent.invoke(ListEvents.ChangeTheme(FIRST_THEME))
                1 -> onUiEvent.invoke(ListEvents.ChangeTheme(SECOND_THEME))
                2 -> onUiEvent.invoke(ListEvents.ChangeTheme(THIRD_THEME))
            }
        }
    ) { onUiEvent.invoke(ListEvents.ShowSettingsDialog(false)) }
}

@Preview(name = "ShowSettingsDialog", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ShowSettingsDialogPreview() {
    ThemeSettings {
        {}
    }
}
