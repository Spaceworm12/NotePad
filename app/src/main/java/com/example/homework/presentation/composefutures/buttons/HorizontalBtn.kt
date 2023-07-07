package com.example.homework.presentation.composefutures.buttons

import NotesTheme
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
fun HorizontalBtn(
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean = true,
    minWidth: Dp = 120.dp,
    minHeight: Dp = 38.dp,
    color: Color = NotesTheme.colors.rippleColor,
    bottomPadding: Dp = NotesTheme.dimens.sideMargin,
    onClick: () -> Unit,
) {
    with(NotesTheme.dimens) {
        Row(
            modifier = modifier.fillMaxWidth()
                .padding(bottom = bottomPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            val shape = RoundedCornerShape(contentMargin)

            Button(
                modifier = Modifier
                    .fillMaxWidth()
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
                    text = text,
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
            PrimaryBtn(text = "Text", isEnabled = false) {}
            PrimaryBtn(text = "Text", color = NotesTheme.colors.error) {}
        }
    }
}

