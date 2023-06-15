package com.example.homework.presentation.detail

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
import com.example.homework.databinding.FragmentNoteBinding
import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.model.NoteType
import com.example.homework.presentation.recycler.NotesFragment
import java.util.*


class NoteFragment : Fragment() {

    companion object {
        private const val KEY_NOTE = "KEY_NOTE"

        fun newInstance(note: NoteModel) = NoteFragment().apply {
            arguments = bundleOf(
                KEY_NOTE to note
            )
        }
    }

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private val noteViewModel: NoteViewModel by lazy {
        ViewModelProvider(this)[NoteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val note =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) requireArguments().getParcelable(
                KEY_NOTE,
                NoteModel::class.java
            ) ?: getEmptyNote() else requireArguments().getParcelable(KEY_NOTE) ?: getEmptyNote()

        binding.noteName.setText(note.name)
        binding.noteDescriprion.setText(note.description)
        binding.bdDate.setText(note.date)
        noteViewModel.submitUIEvent(NoteEvent.SaveUserTitle(note.name))
        noteViewModel.submitUIEvent(NoteEvent.SaveUserDescription(note.description))
        noteViewModel.submitUIEvent(NoteEvent.SaveUserDate(note.date))

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
        binding.bdDate.setOnClickListener{
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
                    NotesFragment()
                )
                .commit()
        }
        noteViewModel.exit.observe(viewLifecycleOwner) { isExit ->
            if (isExit)
                requireActivity().supportFragmentManager.popBackStackImmediate()
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
                    NotesFragment()
                )
                .commit()


        }
        noteViewModel.errorText.observe(viewLifecycleOwner) {
            if (it.isNotBlank()) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}





