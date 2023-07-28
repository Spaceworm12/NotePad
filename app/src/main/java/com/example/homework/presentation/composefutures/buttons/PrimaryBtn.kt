package com.example.homework.presentation.composefutures.buttons

import com.example.homework.presentation.composefutures.NotesTheme
import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.homework.presentation.composefutures.ThemeSettings


@Composable
fun PrimaryBtn(
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean = true,
    minWidth: Dp = 180.dp,
    minHeight: Dp = 48.dp,
    color: Color = NotesTheme.colors.secondary,
    bottomPadding: Dp = NotesTheme.dimens.sideMargin,
    onClick: () -> Unit,
) {
    with(NotesTheme.dimens) {
        Row(
            modifier = modifier
                .padding(bottom = bottomPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            val shape = RoundedCornerShape(contentMargin)

            Button(
                modifier = Modifier.wrapContentSize()
                    .widthIn(min = minWidth)
                    .heightIn(min = minHeight)
                    .border(
                        width = 2.dp,
                        color = if (isEnabled) {
                            color
                        } else {
                            NotesTheme.colors.notEnabled
                        },
                        shape = shape
                    ),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = NotesTheme.colors.primary,
                    contentColor = color,
                    disabledBackgroundColor = NotesTheme.colors.primary,
                    disabledContentColor = NotesTheme.colors.notEnabled,
                ),
                shape = shape,
                enabled = isEnabled,
                onClick = { onClick.invoke() },
            )
            {
                Text(
                    fontSize = 18.sp,
                    style = NotesTheme.typography.subtitle1,
                    color= NotesTheme.colors.error,
                    text = text,
                    maxLines = 1,
                )
            }

        }
    }
}

@Preview(name = "PrimaryBtn", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun PrimaryBtnPreview() {
    ThemeSettings {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            PrimaryBtn(modifier = Modifier.fillMaxWidth(), text = "Text") {}
            PrimaryBtn(text = "Сохранить", isEnabled = false) {}
            PrimaryBtn(text = "Text3", color = NotesTheme.colors.error) {}
        }
    }
}

