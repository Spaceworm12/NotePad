package com.example.homework.presentation.recycler.notelistcomponents

import NotesTheme
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.homework.R
import com.example.homework.presentation.composefutures.FIRST_THEME
import com.example.homework.presentation.composefutures.SECOND_THEME
import com.example.homework.presentation.composefutures.THIRD_THEME
import com.example.homework.presentation.composefutures.ThemeSettings
import com.example.homework.presentation.recycler.ListEvents

@Composable
internal fun ShowSettingsDialogRadio(
    currentTheme: Int,
    onUiEvent: (ListEvents) -> Unit,
    onDismiss: () -> Unit,
    onToastShow: (Int) -> Unit,
) {

    val themes = arrayOf(
        ThemeModel(stringResource(R.string.first_theme), FIRST_THEME),
        ThemeModel(stringResource(R.string.second_theme), SECOND_THEME),
        ThemeModel(stringResource(R.string.third_theme), THIRD_THEME),
    )

    Dialog(
        onDismissRequest = { onDismiss.invoke() },
        DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = NotesTheme.colors.primary,
                    shape = RoundedCornerShape(NotesTheme.dimens.sideMargin)
                )
                .padding(top = NotesTheme.dimens.contentMargin, bottom = NotesTheme.dimens.contentMargin),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val selectedItem = remember {
                mutableStateOf(0)
            }
            Text(
                modifier = Modifier.padding(bottom = NotesTheme.dimens.sideMargin),
                color = NotesTheme.colors.rippleColor,
                text = stringResource(R.string.select_theme),
                fontSize = 22.sp,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Start
            )

            (themes.indices).forEach { index ->

                val theme = themes[index]

                val onClick: () -> Unit = {
                    if (currentTheme != theme.code) {
                        selectedItem.value = theme.code
                        onUiEvent.invoke(ListEvents.ChangeTheme(theme.code))
                    } else {
                        onToastShow.invoke(R.string.theme_already_run)
                    }
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .clickable { onClick.invoke() },
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RadioButton(
                        selected = (currentTheme == theme.code),
                        onClick = { onClick.invoke() },
                        colors = RadioButtonDefaults.colors(Color.DarkGray)
                    )

                    Text(
                        modifier = Modifier.padding(start = NotesTheme.dimens.sideMargin),
                        color = NotesTheme.colors.rippleColor,
                        text = theme.name,
                        fontSize = 22.sp,
                        textAlign = TextAlign.End
                    )

                }
            }
        }
    }
}

private data class ThemeModel(
    val name: String,
    val code: Int
)

@Preview(name = "ShowSettingsDialogRadio", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ShowSettingsDialogRadioPreview() {
    ThemeSettings {
        ShowSettingsDialogRadio(currentTheme = FIRST_THEME, onUiEvent = {}, {}) {

        }
    }
}
