package com.example.homework.presentation.recycler.notelistcomponents

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.homework.R
import com.example.homework.presentation.composefutures.FIRST_THEME
import com.example.homework.presentation.composefutures.SECOND_THEME
import com.example.homework.presentation.composefutures.THIRD_THEME
import com.example.homework.presentation.composefutures.ThemeSettings
import com.example.homework.presentation.recycler.ListEvents

@Composable
internal fun ShowSettingsDialogRadio(
    currentTheme: Int,
    onUiEvent: (ListEvents) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        val items = listOf(FIRST_THEME, SECOND_THEME, THIRD_THEME)

        val selectedItem = remember {
            mutableStateOf(0)
        }
        Text(color = Color.White, text = stringResource(R.string.select_theme), fontSize = 18.sp)
        Spacer(modifier = Modifier.size(16.dp))

        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {

            RadioButton(
                enabled=(currentTheme==FIRST_THEME),
                selected = selectedItem.value == currentTheme, onClick = {
                selectedItem.value = FIRST_THEME
                onUiEvent.invoke(ListEvents.ChangeTheme(FIRST_THEME))
            }, colors = RadioButtonDefaults.colors(Color.Green))
            Spacer(modifier = Modifier.size(16.dp))
            Text(color = Color.White, text = stringResource(R.string.first_theme))
            Spacer(modifier = Modifier.size(16.dp))
        }
        Row {
            RadioButton(enabled=(currentTheme==SECOND_THEME),
                selected = selectedItem.value == currentTheme, onClick = {
                selectedItem.value = SECOND_THEME
                onUiEvent.invoke(ListEvents.ChangeTheme(SECOND_THEME))
            }, colors = RadioButtonDefaults.colors(Color.Green))
            Spacer(modifier = Modifier.size(16.dp))
            Text(color = Color.White, text = stringResource(R.string.second_theme))
            Spacer(modifier = Modifier.size(16.dp))
        }
        Row {
            RadioButton(enabled=(currentTheme==THIRD_THEME),selected = selectedItem.value == currentTheme, onClick = {
                selectedItem.value = THIRD_THEME
                onUiEvent.invoke(ListEvents.ChangeTheme(THIRD_THEME))
            }, colors = RadioButtonDefaults.colors(Color.Green))
            Spacer(modifier = Modifier.size(16.dp))
            Text(color= Color.White,text= stringResource(R.string.third_theme))
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@Preview(name = "ShowSettingsDialogRadio", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ShowSettingsDialogRadioPreview() {
    ThemeSettings {
        ShowSettingsDialogRadio(currentTheme = FIRST_THEME)
        {}
    }
}
