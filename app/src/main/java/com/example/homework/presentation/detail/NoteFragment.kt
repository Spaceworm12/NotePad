package com.example.homework.presentation.detail

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.homework.databinding.FragmentNoteBinding
import com.example.homework.presentation.model.NoteModel


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
        binding.btnSave.setOnClickListener {
            noteViewModel.submitUIEvent(NoteEvent.SaveNote(note.id))
        }
        noteViewModel.exit.observe(viewLifecycleOwner) { isExit ->
            if (isExit)
                requireActivity().supportFragmentManager.popBackStackImmediate()
        }
        binding.btnBack.setOnClickListener {
//            noteViewModel.exit.observe(viewLifecycleOwner) {
                noteViewModel.exit.postValue(true)
            }
            binding.btnDelete.setOnClickListener {
                noteViewModel.submitUIEvent(NoteEvent.DeleteNote(note.id))
            }
        }


    private fun getEmptyNote(): NoteModel {
        return NoteModel(
            id = 0,
            name = "",
            description = ""
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





