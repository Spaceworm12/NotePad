package com.example.homework.presentation.detail

import android.app.DatePickerDialog
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.homework.R
import com.example.homework.presentation.composefutures.ComposeFragment
import com.example.homework.presentation.composefutures.ThemeSettings
import com.example.homework.presentation.composefutures.toolbarsandloader.LoaderBlock
import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.model.NoteType
import com.example.homework.presentation.recycler.ListFragment
import java.util.*

class NoteFragment : ComposeFragment() {

    companion object {
        private const val KEY_NOTE = "KEY_NOTE"

        fun newInstance(note: NoteModel) = NoteFragment().apply {
            arguments = bundleOf(
                KEY_NOTE to note
            )
        }
    }

    private val noteViewModel: NoteViewModel by lazy {
        ViewModelProvider(this)[NoteViewModel::class.java]
    }

    @Composable
    override fun GetContent() {
        noteViewModel.viewState = noteViewModel.viewState.copy(loading = true)
        val note =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) requireArguments().getParcelable(
                KEY_NOTE,
                NoteModel::class.java
            ) ?: getEmptyNote() else requireArguments().getParcelable(KEY_NOTE) ?: getEmptyNote()
        noteViewModel.viewState = noteViewModel.viewState.copy(loading = false)
        setValues(note)
        setTextWatchers()
        setClicks(note)
        observeViewModel()

        ThemeSettings(themeCode = noteViewModel.viewState.currentTheme) {
            DetailNote(note = note, exit = true)

        }
    }

    @Composable
    private fun DetailNote(note: NoteModel, exit: Boolean) {

        if (exit) goBack()

        var currentName by remember { mutableStateOf("") }
        currentName = currentName.ifBlank { item.name }

        var currentDescription by remember { mutableStateOf("") }
        currentDescription = currentDescription.ifBlank { item.description }


        Column(modifier = Modifier.background(AppTheme.colors.background)) {

            Toolbar(
                title = stringResource(id = R.string.detail_fragment),
                onBackClick = { goBack() }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.dimens.halfContentMargin)
                    .border(
                        width = 1.dp,
                        color = AppTheme.colors.secondaryVariant,
                        shape = RoundedCornerShape(AppTheme.dimens.contentMargin)
                    )
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = currentName,
                    onValueChange = {
                        currentName = it
                        item.name = it
                        viewModel.submitUIEvent(DetailEvent.SetItem(item))
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.label_hint),
                            style = AppTheme.typography.body1
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = AppTheme.colors.secondary
                    ),
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(AppTheme.dimens.halfContentMargin)
                    .border(
                        width = 1.dp,
                        color = AppTheme.colors.secondaryVariant,
                        shape = RoundedCornerShape(AppTheme.dimens.contentMargin)
                    )
            ) {
                TextField(
                    modifier = Modifier.fillMaxSize(),
                    value = currentDescription,
                    onValueChange = {
                        currentDescription = it
                        item.description = it
                        viewModel.submitUIEvent(DetailEvent.SetItem(item))
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.description_hint),
                            style = AppTheme.typography.body1
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = AppTheme.colors.secondary
                    ),
                )
            }

            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.save),
                isEnabled = currentName.isNotBlank() && currentDescription.isNotBlank()
            ) {
                viewModel.submitUIEvent(DetailEvent.SaveItem(item.id))
            }

        }

    }

    private fun setValues(note: NoteModel) {
        note.name = noteViewModel.viewState.userTitle.observeAsState().value ?: return
        noteViewModel.viewState = note.observeAsState().value ?: return
        val currentTheme = currentTheme.observeAsState().value ?: return
        val exit = noteViewModel.exit.observeAsState().value ?: return
//        binding.noteName.setText(note.name)
//        binding.noteDescriprion.setText(note.description)
//        binding.bdDate.setText(note.date)
        noteViewModel.submitUIEvent(NoteEvent.SaveUserTitle(note.name))
        noteViewModel.submitUIEvent(NoteEvent.SaveUserDescription(note.description))
        noteViewModel.submitUIEvent(NoteEvent.SaveUserDate(note.date))
    }

    private fun setTextWatchers() {
        binding.noteName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    noteViewModel.submitUIEvent(NoteEvent.SaveUserTitle(s.toString()))
                }
            }
        })
        binding.noteDescriprion.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    noteViewModel.submitUIEvent(NoteEvent.SaveUserDescription(s.toString()))
                }
            }
        })
    }

    private fun setClicks(note: NoteModel) {
        binding.bdDate.setOnClickListener {
            val d = Calendar.getInstance()
            val year = d.get(Calendar.YEAR)
            val month = d.get(Calendar.MONTH)
            val day = d.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    val s = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    binding.bdDate.text = s
                    noteViewModel.submitUIEvent(NoteEvent.SaveUserDate(s))
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
        binding.btnSave.setOnClickListener {
            noteViewModel.submitUIEvent(NoteEvent.SaveNote(note.id))
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    ListFragment()
                )
                .commit()
        }
        binding.btnBack.setOnClickListener {
            noteViewModel.submitUIEvent(NoteEvent.Exit)
        }
        binding.btnDelete.setOnClickListener {
            noteViewModel.submitUIEvent(NoteEvent.DeleteNote(note.id))
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    ListFragment()
                )
                .commit()
        }
    }

    private fun observeViewModel() {
        noteViewModel.viewStateObs.observe(viewLifecycleOwner) { state ->
            if (state.loading)
                LoaderBlock(text = "ABOBA")
            if (state.exit)
                requireActivity().supportFragmentManager.popBackStackImmediate()
            if (state.errorText.isNotBlank()) {
                Toast.makeText(context, "ERR%$@", Toast.LENGTH_SHORT).show()
                noteViewModel.submitUIEvent(NoteEvent.Error)
            }
        }
    }

    private fun getEmptyNote(): NoteModel {
        return NoteModel(
            id = 0,
            name = "",
            description = "",
            type = NoteType.NOTE_TYPE,
            date = ""
        )
    }

}





