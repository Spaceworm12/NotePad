package com.example.homework.presentation.detailBd

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.homework.R
import com.example.homework.databinding.FragmentBirthdayBinding
import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.model.NoteType
import com.example.homework.presentation.recycler.ListFragment
import java.util.*

class BirthdayFragment : Fragment() {

    companion object {
        private const val KEY_NOTE = "KEY_NOTE"

        fun newInstance(note: NoteModel) = BirthdayFragment().apply {
            arguments = bundleOf(
                KEY_NOTE to note
            )
        }
    }
    private var _binding: FragmentBirthdayBinding? = null
    private val binding get() = _binding!!
    private val bdViewModel: BirthdayViewModel by lazy {
        ViewModelProvider(this)[BirthdayViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBirthdayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val note =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) requireArguments().getParcelable(
                KEY_NOTE,
                NoteModel::class.java
            ) ?: getEmptyBd() else requireArguments().getParcelable(KEY_NOTE) ?: getEmptyBd()

        setNoteValues(note)
        viewStateObsSet()
        bindButtons(note)
        submitUiEventsUser(note)
    }

    private fun bindButtons(note: NoteModel) {
        binding.btnPicker.setOnClickListener {
            val x = Calendar.getInstance()
            val y = x.get(Calendar.YEAR)
            val m = x.get(Calendar.MONTH)
            val d = x.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    binding.selectedDate.text =
                        (dayOfMonth.toString()+ "-" + (monthOfYear + 1) + "-" + year)
                },
                y,
                m,
                d
            )
            datePickerDialog.show()
        }
        setTextWatchers()
        binding.btnSaveBd.setOnClickListener {
            bdViewModel.submitUIEvent(BirthdayEvent.SaveBirth(note.id))
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    ListFragment()
                )
                .commit()
        }

        binding.btnCancelBd.setOnClickListener {
            bdViewModel.submitUIEvent(BirthdayEvent.Exit)
        }
    }

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

    private fun setNoteValues(note: NoteModel) {
        binding.user.setText(note.name)
        binding.noteDescriprion.setText(note.description)
        binding.selectedDate.setText(note.date)
    }

    private fun setTextWatchers() {
        binding.selectedDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    bdViewModel.submitUIEvent(BirthdayEvent.SaveBirthDate(s.toString()))
                }
            }
        })
        binding.user.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    bdViewModel.submitUIEvent(BirthdayEvent.SaveUserBirth(s.toString()))
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
                    bdViewModel.submitUIEvent(BirthdayEvent.SaveUserDescription(s.toString()))
                }
            }
        })
    }

    private fun submitUiEventsUser(note: NoteModel) {
        bdViewModel.submitUIEvent(BirthdayEvent.SaveUserBirth(note.name))
        bdViewModel.submitUIEvent(BirthdayEvent.SaveUserDescription(note.description))
        bdViewModel.submitUIEvent(BirthdayEvent.SaveBirthDate(note.date))
    }

    private fun getEmptyBd(): NoteModel {
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
