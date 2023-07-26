package com.example.homework.presentation.composefutures.dialogs

import NotesTheme
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.homework.presentation.composefutures.ThemeSettings
import com.example.homework.presentation.composefutures.buttons.DialogBtn

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EventsDialog(
    title: String = "",
    subTitle: String = "",
    negativeButtonText: String = "",
    isEnabled: Boolean = true,
    items: Array<String>,
    onItemClick: (Int) -> Unit,
    dismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { dismiss.invoke() },
        DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = NotesTheme.colors.primary,
                    shape = RoundedCornerShape(NotesTheme.dimens.sideMargin)
                )
                .padding(
                    top = if (title.isBlank() && subTitle.isBlank()) 0.dp else NotesTheme.dimens.sideMargin,
                    bottom = if (negativeButtonText.isNotBlank()) 0.dp else NotesTheme.dimens.sideMargin,
                    start = NotesTheme.dimens.sideMargin,
                    end = NotesTheme.dimens.sideMargin
                )
        ) {
            if (title.isNotBlank())
                Text(
                    text = title,
                    style = NotesTheme.typography.subtitle1,
                    modifier = Modifier.padding(bottom = NotesTheme.dimens.halfContentMargin)
                )


            if (subTitle.isNotBlank())
                Text(
                    text = subTitle,
                    style = NotesTheme.typography.caption,
                    color = NotesTheme.colors.notEnabled
                )

            (items.indices).forEach { index ->

                CompositionLocalProvider(
                    LocalMinimumInteractiveComponentEnforcement provides false,
                ) {
                    TextButton(
                        onClick = { onItemClick.invoke(index) },
                        enabled = isEnabled,
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = if (index == 0) NotesTheme.dimens.contentMargin
                                else NotesTheme.dimens.halfContentMargin
                            )
                    ) {
                        Text(
                            text = items[index],
                            style = NotesTheme.typography.body1,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }

            if (negativeButtonText.isNotBlank())
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    DialogBtn(
                        text = negativeButtonText,
                        onClick = { dismiss.invoke() }
                    )
                }
        }
    }
}

@Preview(name = "EventsDialogPreview", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun EventsDialogPreview() {
    ThemeSettings(themeCode = 1) {
        EventsDialog(
            "1",
            "2",
            "3",
            true,
            arrayOf("1", "2", "3"),
            {},
            {}
        )
    }
}
