package com.example.homework.presentation.detailbd

import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.homework.presentation.composefutures.ComposeFragment
import com.example.homework.presentation.composefutures.ThemeSettings
import com.example.homework.presentation.detail.NoteEvent
import com.example.homework.presentation.detail.NoteFragment
import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.model.NoteType
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
                NoteFragment.KEY_NOTE,
                NoteModel::class.java
            ) ?: getEmptyNote() else requireArguments().getParcelable(NoteFragment.KEY_NOTE) ?: getEmptyNote()

        viewModel.submitUIEvent(NoteEvent.SetNote(note))
        val noteExample = viewModel.noteExample.observeAsState().value ?: return
        val currentTheme = viewModel.currentTheme.observeAsState().value ?: return
        val exit = viewModel.exit.observeAsState().value ?: return

        ThemeSettings(themeCode = currentTheme) {
            DetailNote(noteExample, exit)
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

        private fun viewStateObsSet() {
            bdViewModel.viewStateObs.observe(viewLifecycleOwner) { state ->
                if (state.exit) {
                    requireActivity().supportFragmentManager.popBackStackImmediate()
                }
                if (state.errorText.isNotBlank()) {
                    Toast.makeText(context, "ERROR!!", Toast.LENGTH_SHORT).show()
                    bdViewModel.submitUIEvent(BirthdayEvent.Error)
                }
            }
        }

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

        private fun getEmptyBirthdayNote(): NoteModel {
            return NoteModel(
                id = 0,
                name = "",
                description = "",
                type = NoteType.BIRTHDAY_TYPE,
                date = "Date"
            )
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }
