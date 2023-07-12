package com.example.homework.presentation.composefutures.dialogs

import NotesTheme
import android.app.DatePickerDialog
import android.content.res.Configuration
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.homework.presentation.composefutures.ComposeFragment
import com.example.homework.presentation.composefutures.ThemeSettings
import com.example.homework.presentation.composefutures.toolbarsandloader.Toolbar
import java.util.*


class TrainingScreen() : ComposeFragment() {
    companion object {
        private const val CLICKS = 0
    }

    @Composable
    override fun GetContent() {
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TrainingScreenSettings() {
        val message by remember { mutableStateOf("message") }

        val enabled by remember { mutableStateOf(true) }

        val click = remember { mutableStateOf(1) }

        Column(modifier = Modifier.background(NotesTheme.colors.background)) {
            Toolbar(
                title = "toolbar title",
                subtitle = "subtitle",
                isBackArrowVisible = true,
                onBackClick = {
                    (Toast.makeText(context, "back", Toast.LENGTH_SHORT).show())
                })
            Box(
                modifier = Modifier
                    .background(Color.Yellow)
                    .wrapContentWidth()
                    .height(150.dp)
                    .weight(1f)
                    .border(
                        width = 1.dp,
                        color = NotesTheme.colors.secondaryVariant,
                        shape = RoundedCornerShape(NotesTheme.dimens.contentMargin)
                    )
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    val mContext = LocalContext.current
                    val mYear: Int
                    val mMonth: Int
                    val mDay: Int
                    val mCalendar = Calendar.getInstance()
                    mYear = mCalendar.get(Calendar.YEAR)
                    mMonth = mCalendar.get(Calendar.MONTH)
                    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
                    mCalendar.time = Date()
                    val mDate = remember { mutableStateOf("") }
                    val mDatePickerDialog = DatePickerDialog(
                        mContext,
                        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int->
                            mDate.value = "$mDayOfMonth.${mMonth+1}.$mYear"
                        }, mYear, mMonth, mDay
                    )
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(onClick = {
                            mDatePickerDialog.show()
                        }) {
                            Text(text = "Open Date Picker")
                        }
                        Spacer(modifier = Modifier.size(100.dp))
                        Text(text = "Selected Date: ${mDate.value}", fontSize = 30.sp, textAlign = TextAlign.Center)
                    }
                }
                    }
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
                    .clickable(onClick = { click.value++ })
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