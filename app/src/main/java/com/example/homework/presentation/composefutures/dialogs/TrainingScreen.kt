package com.example.homework.presentation.composefutures.dialogs

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.homework.presentation.composefutures.ComposeFragment
import com.example.homework.presentation.composefutures.ThemeSettings

class TrainingScreen() : ComposeFragment() {
    @Composable
    override fun GetContent() {
    }

    @Composable
    private fun TrainingScreenSettings() {
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
                    )
                    .background(Color.Red)
                    .horizontalScroll(rememberScrollState(), true, null, false)


            )
            Box(
                modifier = Modifier
                    .background(Color.Green)
                    .fillMaxWidth()
                    .height(150.dp)
                    .weight(1f)
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