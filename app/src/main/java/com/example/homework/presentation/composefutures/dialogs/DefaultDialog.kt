package com.example.homework.presentation.composefutures.dialogs

import NotesTheme
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.homework.R
import com.example.homework.presentation.composefutures.ThemeSettings
import com.example.homework.presentation.composefutures.buttons.DialogBtn


@Composable
fun DefaultDialog(
    title: String,
    message: String = "",
    negativeButtonText: String = "",
    positiveButtonText: String = "",
    negativeButtonColor: Color? = null,
    positiveButtonColor: Color? = null,
    isEnabled: Boolean = true,
    onPositiveClick: () -> Unit,
    onNegativeClick: (() -> Unit)? = null,
    dismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { dismiss.invoke() },
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = true)
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = NotesTheme.colors.primary,
                    shape = RoundedCornerShape(NotesTheme.dimens.sideMargin)
                )
                .padding(
                    end = NotesTheme.dimens.sideMargin,
                    start = NotesTheme.dimens.sideMargin,
                    top = NotesTheme.dimens.sideMargin
                )
        ) {
            Text(
                text = title,
                style = NotesTheme.typography.subtitle1,
                modifier = Modifier
                    .padding(bottom = NotesTheme.dimens.sideMargin)
                    .fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = NotesTheme.dimens.contentMargin),
                horizontalArrangement = Arrangement.End,
            ) {
                DialogBtn(
                    text = negativeButtonText.ifBlank { stringResource(R.string.no) },
                    isEnabled = isEnabled,
                    onClick = { if (onNegativeClick != null) onNegativeClick.invoke() else dismiss.invoke() },
                    color = negativeButtonColor ?: NotesTheme.colors.secondary
                )
                DialogBtn(
                    text = positiveButtonText.ifBlank { stringResource(R.string.yes) },
                    isEnabled = isEnabled,
                    onClick = { onPositiveClick.invoke() },
                    color = positiveButtonColor ?: NotesTheme.colors.secondary
                )
            }
        }
    }
}

@Preview(name = "DefaultDialog", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun DefaultDialogPreview() {
    ThemeSettings {
        DefaultDialog(title = "Уверен?",
            message = "Ну жми. Но только аккуратно. Пока жмешь, проверим как смотрится длинный текст. А вообще иди в сраку. Делать тебе тут нечго и вообще андроид не для тебя.",
            onPositiveClick = {}) {}
    }
}
