package com.example.homework.presentation.detailbd

import NotesTheme
import android.app.DatePickerDialog
import android.content.res.Configuration
import android.os.Build
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.homework.R
import com.example.homework.presentation.composefutures.ComposeFragment
import com.example.homework.presentation.composefutures.ThemeSettings
import com.example.homework.presentation.composefutures.buttons.PrimaryBtn
import com.example.homework.presentation.composefutures.toolbarsandloader.Toolbar
import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.model.NoteType
import java.util.Calendar
import java.util.Date

class BirthdayFragment : ComposeFragment() {

    companion object {
        private const val KEY_NOTE = "KEY_NOTE"

        fun newInstance(note: NoteModel) = BirthdayFragment().apply {
            arguments = bundleOf(
                KEY_NOTE to note
            )
        }
    }

    private val bdViewModel: BirthdayViewModel by lazy {
        ViewModelProvider(this)[BirthdayViewModel::class.java]
    }

    @Composable
    override fun GetContent() {
        val note =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) requireArguments().getParcelable(
                KEY_NOTE,
                NoteModel::class.java
            ) ?: getEmptyBirthdayNote() else requireArguments().getParcelable(KEY_NOTE)
                ?: getEmptyBirthdayNote()

        bdViewModel.submitUIEvent(BirthdayEvent.SetBirthdayNote(note))
        val bdNoteExample = bdViewModel.bdNoteExample.observeAsState().value ?: return
        val currentTheme = bdViewModel.currentTheme.observeAsState().value ?: return
        val exit = bdViewModel.exit.observeAsState().value ?: return

        ThemeSettings(themeCode = currentTheme) {
            DetailBirthdayNote(bdNoteExample, exit)
        }
    }

    @Composable
    private fun DetailBirthdayNote(note: NoteModel, exit: Boolean) {

        if (exit) goBack()

        var currentTitle by remember { mutableStateOf("") }
        currentTitle = currentTitle.ifBlank { note.name }

        var currentDescription by remember { mutableStateOf("") }
        currentDescription = currentDescription.ifBlank { note.description }

        var currentDate by remember { mutableStateOf("") }
        currentDate = currentDate.ifBlank { note.date }

        val context = LocalContext.current
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val date = remember { mutableStateOf("") }
        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                date.value = "$mDayOfMonth.${mMonth + 1}.$mYear"
                currentDate = date.value
                note.date = currentDate
            },
            year,
            month,
            day
        )

        Column(modifier = Modifier.background(NotesTheme.colors.background)) {

            Toolbar(
                title = stringResource(id = R.string.detail_bdnote_toolbar),
                subtitle = stringResource(id = R.string.detail_bdnote_subtoolbar),
                onBackClick = {
                    goBack()
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(NotesTheme.dimens.halfContentMargin)
                    .border(
                        width = 1.dp,
                        color = NotesTheme.colors.secondary,
                        shape = RoundedCornerShape(NotesTheme.dimens.contentMargin)
                    )
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = currentTitle,
                    onValueChange = {
                        currentTitle = it
                        note.name = it
                        bdViewModel.submitUIEvent(BirthdayEvent.SetBirthdayNote(note))
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.input_note_name),
                            style = NotesTheme.typography.body1
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = NotesTheme.colors.primary
                    ),
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(NotesTheme.dimens.halfContentMargin)
                    .border(
                        width = 1.dp,
                        color = NotesTheme.colors.secondary,
                        shape = RoundedCornerShape(NotesTheme.dimens.contentMargin)
                    ),
                contentAlignment = Alignment.Center,
            ) {

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            datePickerDialog.show()
                        },
                    enabled = false,
                    value = note.date,
                    onValueChange = {
                        currentDate = it
                        note.date = currentDate
                        bdViewModel.submitUIEvent(BirthdayEvent.SetBirthdayNote(note))
                    },
                    placeholder = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.date_birthday),
                            textAlign = TextAlign.Center,
                            style = NotesTheme.typography.body1,
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = NotesTheme.colors.primary
                    ),
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(NotesTheme.dimens.halfContentMargin)
                    .border(
                        width = 1.dp,
                        color = NotesTheme.colors.secondaryVariant,
                        shape = RoundedCornerShape(NotesTheme.dimens.contentMargin)
                    )
            ) {
                TextField(
                    modifier = Modifier.fillMaxSize(),
                    value = currentDescription,
                    onValueChange = {
                        currentDescription = it
                        note.description = currentDescription
                        bdViewModel.submitUIEvent(BirthdayEvent.SetBirthdayNote(note))
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.input_description_hint),
                            style = NotesTheme.typography.body1
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = NotesTheme.colors.secondary
                    ),
                )
            }

            PrimaryBtn(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.save),
                isEnabled = currentTitle.isNotBlank() && currentDescription.isNotBlank()
            ) {
                bdViewModel.submitUIEvent(BirthdayEvent.SaveBirthdayNote(note.id))
            }

        }

    }

    private fun goBack() = requireActivity().supportFragmentManager.popBackStack()

    private fun getEmptyBirthdayNote(): NoteModel {
        return NoteModel(
            id = 0,
            name = "",
            description = "",
            type = NoteType.BIRTHDAY_TYPE,
            date = "Date"
        )
    }

    @Preview(name = "BirthdayNoteScreen", uiMode = Configuration.UI_MODE_NIGHT_MASK)
    @Composable
    private fun BirthdayNoteScreenPreview() {
        ThemeSettings() {

            val model = NoteModel(
                id = 5,
                name = "",
                description = "",
                date = "00.00.00",
                type = NoteType.BIRTHDAY_TYPE,
            )
            DetailBirthdayNote(
                note = model,
                exit = false
            )
        }
    }
}
