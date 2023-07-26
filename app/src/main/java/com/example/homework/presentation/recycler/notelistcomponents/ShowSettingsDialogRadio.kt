package com.example.homework.presentation.recycler.notelistcomponents

import NotesTheme
import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
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
    context: Context?=null,
    currentTheme: Int,
    onUiEvent: (ListEvents) -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismiss.invoke() },
        DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .size(250.dp)
                .background(
                    NotesTheme.colors.primary, shape = RoundedCornerShape(15.dp)
                ),
        ) {
            val selectedItem = remember {
                mutableStateOf(0)
            }
            Text(
                color = NotesTheme.colors.rippleColor,
                text = stringResource(R.string.select_theme),
                fontSize = 22.sp,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.size(16.dp))

            Row(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()) {

                RadioButton(
                    selected = (currentTheme == FIRST_THEME), onClick = {
                        if (currentTheme != FIRST_THEME) {
                            selectedItem.value = FIRST_THEME
                            onUiEvent.invoke(ListEvents.ChangeTheme(FIRST_THEME))
                        } else {
                            Toast.makeText(context, R.string.theme_already_run, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }, colors = RadioButtonDefaults.colors(Color.DarkGray)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(modifier = Modifier.padding(top=5.dp),
                    color = NotesTheme.colors.rippleColor,
                    text = stringResource(R.string.first_theme),
                    fontSize = 22.sp,
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.size(16.dp))
            }
            Row(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()) {
                RadioButton(
                    selected = (currentTheme == SECOND_THEME), onClick = {
                        if (currentTheme != SECOND_THEME) {
                            selectedItem.value = SECOND_THEME
                            onUiEvent.invoke(ListEvents.ChangeTheme(SECOND_THEME))
                        } else {
                            Toast.makeText(context, R.string.theme_already_run, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }, colors = RadioButtonDefaults.colors(Color.DarkGray)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(modifier = Modifier.padding(top=5.dp),
                    color = NotesTheme.colors.rippleColor,
                    text = stringResource(R.string.second_theme),
                    fontSize = 22.sp,
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.size(16.dp))
            }
            Row(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()) {
                RadioButton(
                    selected = (currentTheme == THIRD_THEME),
                    onClick = {
                        if (currentTheme != THIRD_THEME) {
                            selectedItem.value = THIRD_THEME
                            onUiEvent.invoke(ListEvents.ChangeTheme(THIRD_THEME))
                        } else {
                            Toast.makeText(context, R.string.theme_already_run, Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    colors = RadioButtonDefaults.colors(Color.DarkGray)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(modifier = Modifier.padding(top=5.dp),
                    color = NotesTheme.colors.rippleColor,
                    text = stringResource(R.string.third_theme),
                    fontSize = 22.sp,
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }

}

@Preview(name = "ShowSettingsDialogRadio", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ShowSettingsDialogRadioPreview() {
    ThemeSettings {
        ShowSettingsDialogRadio(currentTheme = FIRST_THEME, onUiEvent = {}) {
            
        }
    }
}
