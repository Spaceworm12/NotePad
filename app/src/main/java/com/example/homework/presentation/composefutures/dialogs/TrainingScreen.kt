package com.example.homework.presentation.composefutures.dialogs

import NotesTheme
import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.homework.presentation.composefutures.ComposeFragment
import com.example.homework.presentation.composefutures.ThemeSettings
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


class TrainingScreen() : ComposeFragment() {
    companion object {
        private const val CLICKS = 0
    }

    @Composable
    override fun GetContent() {
    }

    @Composable
    private fun TrainingScreenSettings() {
        val message by remember { mutableStateOf("message") }

        val enabled by remember { mutableStateOf(true) }

        val click = remember { mutableStateOf(1) }

        Column(modifier = Modifier.background(NotesTheme.colors.background)) {
            Box(
                modifier = Modifier
                    .background(Color.Yellow)
                    .fillMaxWidth()
                    .height(150.dp)
                    .weight(1f)
                    .border(
                        width = 1.dp,
                        color = NotesTheme.colors.secondaryVariant,
                        shape = RoundedCornerShape(NotesTheme.dimens.contentMargin)
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .absolutePadding(20.dp, 20.dp, 20.dp, 20.dp)
                    .border(
                        width = 1.dp,
                        color = NotesTheme.colors.secondaryVariant,
                        shape = CutCornerShape(NotesTheme.dimens.contentMargin)
                    ).clickable(onClick = { click.value++ })
                    .background(Color.Red),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = click.value.toString(),
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline,
                    fontSize = 48.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Box(
                modifier = Modifier
                    .background(Color.Green)
                    .fillMaxWidth()
                    .height(150.dp)
                    .border(
                        width = 1.dp,
                        color = NotesTheme.colors.secondaryVariant,
                        shape = AbsoluteCutCornerShape(NotesTheme.dimens.contentMargin)
                    )
            )
        }
    }


@Preview(name = "TrainingScreen", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun TrainingScreenShow() {
    ThemeSettings() {
        TrainingScreenSettings()
    }
}
}