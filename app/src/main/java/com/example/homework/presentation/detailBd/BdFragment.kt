package com.example.homework.presentation.detailBd

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
import com.example.homework.databinding.FragmentBdBinding
import com.example.homework.presentation.detail.NoteEvent
import com.example.homework.presentation.detail.NoteFragment
import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.recycler.NotesFragment

class BdFragment: Fragment() {

        companion object {
            private const val KEY_NOTE = "KEY_NOTE"

            fun newInstance(note: NoteModel) = BdFragment().apply {
                arguments = bundleOf(
                    KEY_NOTE to note
                )
            }
        }

        private var _binding: FragmentBdBinding? = null
        private val binding get() = _binding!!
        private val bdViewModel: BdViewModel by lazy {
            ViewModelProvider(this)[BdViewModel::class.java]
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding = FragmentBdBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val note =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) requireArguments().getParcelable(
                    KEY_NOTE,
                    NoteModel::class.java
                ) ?: getEmptyBd() else requireArguments().getParcelable(KEY_NOTE) ?: getEmptyBd()

            binding.user.setText(note.name)
            binding.noteDescriprion.setText(note.description)
            bdViewModel.submitUIEvent(BdEvent.SaveUserBd(note.name))
            bdViewModel.submitUIEvent(BdEvent.SaveUserDescription(note.description))
//            bdViewModel.submitUIEvent(BdEvent.SaveDateBd(note)

            binding.user.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    s?.let {
                        bdViewModel.submitUIEvent(BdEvent.SaveUserBd(s.toString()))
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
                        bdViewModel.submitUIEvent(BdEvent.SaveUserDescription(s.toString()))
                    }
                }
            })

            binding.btnSaveBd.setOnClickListener {
                bdViewModel.submitUIEvent(BdEvent.SaveBd(note.id))
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        NotesFragment()
                    )
                    .commit()
            }
            bdViewModel.exit.observe(viewLifecycleOwner) { isExit ->
                if (isExit)
                    requireActivity().supportFragmentManager.popBackStackImmediate()
            }
            binding.btnCancelBd.setOnClickListener {
                bdViewModel.submitUIEvent(BdEvent.Exit)
            }


            bdViewModel.errorText.observe(viewLifecycleOwner) {
                if (it.isNotBlank()){
                    Toast.makeText(context,it, Toast.LENGTH_SHORT).show()
                    bdViewModel.submitUIEvent(BdEvent.Error)
                }
            }
        }


        private fun getEmptyBd(): NoteModel {
            return NoteModel(
                id = 0,
                name = "",
                description = "" ,
                type ="BIRTHDAY_NOTE"
            )
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }
}