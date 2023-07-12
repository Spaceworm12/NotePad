package com.example.homework.presentation.detailbd

import NotesTheme
import android.app.DatePickerDialog
import android.content.res.Configuration
import android.os.Build
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.homework.R
import com.example.homework.presentation.composefutures.ComposeFragment
import com.example.homework.presentation.composefutures.ThemeSettings
import com.example.homework.presentation.composefutures.buttons.HorizontalBtn
import com.example.homework.presentation.composefutures.buttons.PrimaryBtn
import com.example.homework.presentation.composefutures.toolbarsandloader.Toolbar
import com.example.homework.presentation.detail.NoteFragment
import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.model.NoteType
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

class BirthdayFragment : ComposeFragment() {

    companion object {
        private const val KEY_NOTE = "KEY_NOTE"

        fun newInstance(note: NoteModel) = NoteFragment().apply {
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


        Column(modifier = Modifier.background(NotesTheme.colors.background)) {

            Toolbar(
                title = stringResource(id = R.string.detail_bdnote_toolbar),
                subtitle = stringResource(id = R.string.detail_bdnote_subtoolbar),
                onBackClick = { goBack()
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
            HorizontalBtn(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.select_date),
                isEnabled = true,
            ) {
                mDatePickerDialog.show()
                currentTitle = currentTitle +" "+ mDate.value
                currentDate = mDate.value
                bdViewModel.submitUIEvent(BirthdayEvent.SetBirthdayNote(note))
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
                        bdViewModel.submitUIEvent(BirthdayEvent.SetBirthdayNote(note))
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
    private fun showDatePicker() {
        val picker = MaterialDatePicker.Builder.datePicker().build()
        activity?.let {
            val x = Calendar.getInstance()
                val y = x.get(Calendar.YEAR)
                val m = x.get(Calendar.MONTH)
                val d = x.get(Calendar.DAY_OF_MONTH)
                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    { view, year, monthOfYear, dayOfMonth ->
                            (dayOfMonth.toString()+ "-" + (monthOfYear + 1) + "-" + year)
                    },
                    y,
                    m,
                    d
                )
                datePickerDialog.show()
            }
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


//        override fun onCreateView(
//            inflater: LayoutInflater, container: ViewGroup?,
//            savedInstanceState: Bundle?
//        ): View {
//            _binding = FragmentBirthdayBinding.inflate(inflater, container, false)
//            return binding.root
//        }
//
//        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//            super.onViewCreated(view, savedInstanceState)
//            val note =
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) requireArguments().getParcelable(
//                    KEY_NOTE,
//                    NoteModel::class.java
//                ) ?: getEmptyBd() else requireArguments().getParcelable(KEY_NOTE) ?: getEmptyBd()
//
//            setNoteValues(note)
//            viewStateObsSet()
//            bindButtons(note)
//            submitUiEventsUser(note)
//        }
//
//        private fun bindButtons(note: NoteModel) {
//            binding.btnPicker.setOnClickListener {
//                val x = Calendar.getInstance()
//                val y = x.get(Calendar.YEAR)
//                val m = x.get(Calendar.MONTH)
//                val d = x.get(Calendar.DAY_OF_MONTH)
//                val datePickerDialog = DatePickerDialog(
//                    requireContext(),
//                    { view, year, monthOfYear, dayOfMonth ->
//                        binding.selectedDate.text =
//                            (dayOfMonth.toString()+ "-" + (monthOfYear + 1) + "-" + year)
//                    },
//                    y,
//                    m,
//                    d
//                )
//                datePickerDialog.show()
//            }
//            setTextWatchers()
//            binding.btnSaveBd.setOnClickListener {
//                bdViewModel.submitUIEvent(BirthdayEvent.SaveBirth(note.id))
//                requireActivity()
//                    .supportFragmentManager
//                    .beginTransaction()
//                    .replace(
//                        R.id.fragment_container,
//                        ListFragment()
//                    )
//                    .commit()
//            }
//
//            binding.btnCancelBd.setOnClickListener {
//                bdViewModel.submitUIEvent(BirthdayEvent.Exit)
//            }
//        }

//        private fun viewStateObsSet() {
//            bdViewModel.viewStateObs.observe(viewLifecycleOwner) { state ->
//                if (state.exit) {
//                    requireActivity().supportFragmentManager.popBackStackImmediate()
//                }
//                if (state.errorText.isNotBlank()) {
//                    Toast.makeText(context, "ERROR!!", Toast.LENGTH_SHORT).show()
//                    bdViewModel.submitUIEvent(BirthdayEvent.Error)
//                }
//            }
//        }

//        private fun setNoteValues(note: NoteModel) {
//            binding.user.setText(note.name)
//            binding.noteDescriprion.setText(note.description)
//            binding.selectedDate.setText(note.date)
//        }

//        private fun setTextWatchers() {
//            binding.selectedDate.addTextChangedListener(object : TextWatcher {
//                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                }
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                }
//
//                override fun afterTextChanged(s: Editable?) {
//                    s?.let {
//                        bdViewModel.submitUIEvent(BirthdayEvent.SaveBirthDate(s.toString()))
//                    }
//                }
//            })
//            binding.user.addTextChangedListener(object : TextWatcher {
//                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                }
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                }
//
//                override fun afterTextChanged(s: Editable?) {
//                    s?.let {
//                        bdViewModel.submitUIEvent(BirthdayEvent.SaveUserBirth(s.toString()))
//                    }
//                }
//            })
//
//            binding.noteDescriprion.addTextChangedListener(object : TextWatcher {
//                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                }
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                }
//
//                override fun afterTextChanged(s: Editable?) {
//                    s?.let {
//                        bdViewModel.submitUIEvent(BirthdayEvent.SaveUserDescription(s.toString()))
//                    }
//                }
//            })
//        }
//
//        private fun submitUiEventsUser(note: NoteModel) {
//            bdViewModel.submitUIEvent(BirthdayEvent.SaveUserBirth(note.name))
//            bdViewModel.submitUIEvent(BirthdayEvent.SaveUserDescription(note.description))
//            bdViewModel.submitUIEvent(BirthdayEvent.SaveBirthDate(note.date))
//        }


//        override fun onDestroyView() {
//            super.onDestroyView()
//            _binding = null
//        }
//    }

