package com.example.homework.presentation.composefutures.toolbarsandloader

import NotesTheme
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.homework.presentation.composefutures.ThemeSettings

@Composable
fun LoaderBlock(text: String) {
    Dialog(
        onDismissRequest = { },
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(
                    color = NotesTheme.colors.primary,
                    shape = RoundedCornerShape(NotesTheme.dimens.sideMargin)
                )
                .padding(NotesTheme.dimens.sideMargin)
        ) {
            CircularProgressIndicator(color = NotesTheme.colors.secondary)
            Text(
                modifier = Modifier.padding(start = NotesTheme.dimens.sideMargin),
                text = text,
            )
        }
    }
}

@Preview(name = "LoaderBlock", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun LoaderBlockPreview() {
    ThemeSettings() {
        LoaderBlock("Идет обмен данными с сервером")
    }
}
