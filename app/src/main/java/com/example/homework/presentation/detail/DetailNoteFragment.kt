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
import com.example.homework.R
import com.example.homework.databinding.FragmentTextBinding
import com.example.homework.presentation.model.NoteModel


class DetailNoteFragment : Fragment() {

    companion object {
        private const val KEY_NOTE = "KEY_NOTE"

        fun newInstance(note: NoteModel) = DetailNoteFragment().apply {
            arguments = bundleOf(
                KEY_NOTE to note
            )
        }
    }

    private var _binding: FragmentTextBinding? = null
    private val binding get() = _binding!!
    private val detailNoteViewModel: DetailNoteViewModel by lazy {
        ViewModelProvider(this)[DetailNoteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTextBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Восстанавливаем текст из liveData
        super.onViewCreated(view, savedInstanceState)
        val note =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) requireArguments().getParcelable(
                KEY_NOTE,
                NoteModel::class.java
            ) ?: getEmptyItem() else requireArguments().getParcelable(KEY_NOTE) ?: getEmptyItem()
//        val currentNote: NoteModel =
//            arguments?.getParcelable(KEY_NOTE) ?: NoteModel(id = 0, "123", description = "desc")
//        binding.noteName.text = currentNote.name

//        if (savedInstanceState == null) {
//            binding.noteDescriprion.setText(currentNote.description)
//        } else {
//            binding.noteDescriprion.setText(detailViewModelNote.userText.value ?: "nothing")
//        }

        binding.noteDescriprion.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                //Сохраняем изменения в liveData
                s?.let {
                    detailNoteViewModel.submitUIEvent(DetailEvent.SaveUserDescription(s.toString()))
                }
            }
        })
        binding.noteName.setText(note.name)
        detailNoteViewModel.submitUIEvent(DetailEvent.SaveUserTitle(note.name))
        binding.noteDescriprion.setText(note.description)
        detailNoteViewModel.submitUIEvent(DetailEvent.SaveUserDescription(note.description))
        binding.noteName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    detailNoteViewModel.submitUIEvent(DetailEvent.SaveUserTitle(s.toString()))
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
                    detailNoteViewModel.submitUIEvent(DetailEvent.SaveUserDescription(s.toString()))
                }
            }
        })
        binding.btnSave.setOnClickListener {
            detailNoteViewModel.submitUIEvent(DetailEvent.SaveNote(note.id))
        }
        detailNoteViewModel.exit.observe(viewLifecycleOwner) { isExit ->
            if (isExit)
                requireActivity().supportFragmentManager.popBackStackImmediate()
        }
        binding.btnBack.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.exit_fragment,
                    R.anim.exit_fragment_out
                )
                .remove(this)
                .commit()
        }
    }

    private fun getEmptyItem(): NoteModel {
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





