package com.example.homework.presentation.detail

import NotesTheme
import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
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

class NoteFragment : ComposeFragment() {

    companion object {
        private const val KEY_NOTE = "KEY_NOTE"

        fun newInstance(note: NoteModel) = NoteFragment().apply {
            arguments = bundleOf(
                KEY_NOTE to note
            )
        }
    }

    private val viewModel: NoteViewModel by lazy {
        ViewModelProvider(this)[NoteViewModel::class.java]
    }

    @Composable
    override fun GetContent() {
        val note =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) requireArguments().getParcelable(
                KEY_NOTE,
                NoteModel::class.java
            ) ?: getEmptyNote() else requireArguments().getParcelable(KEY_NOTE) ?: getEmptyNote()

        viewModel.submitUIEvent(NoteEvent.SetNote(note))
        val noteExample = viewModel.noteExample.observeAsState().value ?: return
        val currentTheme = viewModel.currentTheme.observeAsState().value ?: return
        val exit = viewModel.exit.observeAsState().value ?: return

        ThemeSettings(themeCode = currentTheme) {
            DetailNote(noteExample, exit)
        }
    }

    @Composable
    private fun DetailNote(note: NoteModel, exit: Boolean) {

        if (exit) goBack()

        var currentName by remember { mutableStateOf("") }
        currentName = currentName.ifBlank { note.name }

        var currentDescription by remember { mutableStateOf("") }
        currentDescription = currentDescription.ifBlank { note.description }

        var currentDate by remember { mutableStateOf("") }
        currentDate = currentDate.ifBlank { note.date }


        Column(modifier = Modifier.background(NotesTheme.colors.background)) {

            Toolbar(
                title = stringResource(id = R.string.detail_note),
                onBackClick = { goBack() }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(NotesTheme.dimens.halfContentMargin)
                    .border(
                        width = 1.dp,
                        color = NotesTheme.colors.secondaryVariant,
                        shape = RoundedCornerShape(NotesTheme.dimens.contentMargin)
                    )
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = currentName,
                    onValueChange = {
                        currentName = it
                        note.name = it
                        viewModel.submitUIEvent(NoteEvent.SetNote(note))
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
                        cursorColor = NotesTheme.colors.secondary
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
                        note.description = it
                        note.date = it
                        viewModel.submitUIEvent(NoteEvent.SetNote(note))
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.input_description_hint),
                            style = NotesTheme.typography.body1
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
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
                isEnabled = currentName.isNotBlank() && currentDescription.isNotBlank()
            ) {
                viewModel.submitUIEvent(NoteEvent.SaveNote(note.id))
            }

        }

    }

    //    private fun setValues(note: NoteModel) {
//        note.name = noteViewModel.viewState.userTitle.observeAsState().value ?: return
//        noteViewModel.viewState = note.observeAsState().value ?: return
//        val currentTheme = currentTheme.observeAsState().value ?: return
//        val exit = noteViewModel.exit.observeAsState().value ?: return
////        binding.noteName.setText(note.name)
////        binding.noteDescriprion.setText(note.description)
////        binding.bdDate.setText(note.date)
//        noteViewModel.submitUIEvent(NoteEvent.SaveUserTitle(note.name))
//        noteViewModel.submitUIEvent(NoteEvent.SaveUserDescription(note.description))
//        noteViewModel.submitUIEvent(NoteEvent.SaveUserDate(note.date))
//    }
//
//    private fun setTextWatchers() {
//        binding.noteName.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                s?.let {
//                    noteViewModel.submitUIEvent(NoteEvent.SaveUserTitle(s.toString()))
//                }
//            }
//        })
//        binding.noteDescriprion.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                s?.let {
//                    noteViewModel.submitUIEvent(NoteEvent.SaveUserDescription(s.toString()))
//                }
//            }
//        })
//    }
//
//    private fun setClicks(note: NoteModel) {
//        binding.bdDate.setOnClickListener {
//            val d = Calendar.getInstance()
//            val year = d.get(Calendar.YEAR)
//            val month = d.get(Calendar.MONTH)
//            val day = d.get(Calendar.DAY_OF_MONTH)
//            val datePickerDialog = DatePickerDialog(
//                requireContext(),
//                { view, year, monthOfYear, dayOfMonth ->
//                    val s = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
//                    binding.bdDate.text = s
//                    noteViewModel.submitUIEvent(NoteEvent.SaveUserDate(s))
//                },
//                year,
//                month,
//                day
//            )
//            datePickerDialog.show()
//        }
//        binding.btnSave.setOnClickListener {
//            noteViewModel.submitUIEvent(NoteEvent.SaveNote(note.id))
//            requireActivity()
//                .supportFragmentManager
//                .beginTransaction()
//                .replace(
//                    R.id.fragment_container,
//                    ListFragment()
//                )
//                .commit()
//        }
//        binding.btnBack.setOnClickListener {
//            noteViewModel.submitUIEvent(NoteEvent.Exit)
//        }
//        binding.btnDelete.setOnClickListener {
//            noteViewModel.submitUIEvent(NoteEvent.DeleteNote(note.id))
//            requireActivity()
//                .supportFragmentManager
//                .beginTransaction()
//                .replace(
//                    R.id.fragment_container,
//                    ListFragment()
//                )
//                .commit()
//        }
//    }
//
//    private fun observeViewModel() {
//        noteViewModel.viewStateObs.observe(viewLifecycleOwner) { state ->
//            if (state.loading)
//                LoaderBlock(text = "ABOBA")
//            if (state.exit)
//                requireActivity().supportFragmentManager.popBackStackImmediate()
//            if (state.errorText.isNotBlank()) {
//                Toast.makeText(context, "ERR%$@", Toast.LENGTH_SHORT).show()
//                noteViewModel.submitUIEvent(NoteEvent.Error)
//            }
//        }
//    }
    private fun goBack() = requireActivity().supportFragmentManager.popBackStack()

    private fun getEmptyNote(): NoteModel {
        return NoteModel(
            id = 0,
            name = "",
            description = "",
            type = NoteType.NOTE_TYPE,
            date = ""
        )
    }

    @Preview(name = "NoteScreen", uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Composable
    private fun NoteScreenPreview() {
        ThemeSettings() {

            val model = NoteModel(
                id = 0,
                name = "Заметка про Димку",
                description = "Димка, ниче не получится! Ты собака, я собака, ты собака, " +
                        "я собака, ты собака, я собака, ты собака.",
                date = "21.02.22",
                type = NoteType.NOTE_TYPE
            )

            DetailNote(
                note = model,
                exit = false
            )
        }
    }

}





