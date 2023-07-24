package com.example.homework.presentation.composefutures.buttons


import NotesTheme
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.homework.presentation.composefutures.ThemeSettings

@Composable
fun DialogBtn(
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean = true,
    color: Color = NotesTheme.colors.secondary,
    textAlign: TextAlign = TextAlign.End,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.width(IntrinsicSize.Min),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = color,
            disabledBackgroundColor = Color.Transparent,
            disabledContentColor = NotesTheme.colors.notEnabled
        ),
        enabled = isEnabled,
        elevation = null,
        onClick = onClick,
        contentPadding = PaddingValues(0.dp),
    ) {
        Text(
            style = NotesTheme.typography.subtitle1,
            fontSize = 15.sp,
            text = text,
            textAlign = textAlign,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(name = "DialogBtn", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun DialogBtnPreview() {
    ThemeSettings() {
        Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
            DialogBtn(text = "Text1") {}
            DialogBtn(text = "Text2", isEnabled = false) {}
        }
    }
}

