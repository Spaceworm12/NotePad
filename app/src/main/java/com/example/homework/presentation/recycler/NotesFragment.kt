package com.example.homework.presentation.recycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework.R
import com.example.homework.databinding.FragmentNotesBinding
import com.example.homework.presentation.detail.NoteFragment
import com.example.homework.presentation.detailBd.BirthFragment
import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.model.NoteType
import com.example.homework.presentation.recycler.adapter.NotesAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotesViewModel by lazy {
        ViewModelProvider(this)[NotesViewModel::class.java]
    }

    private val adapter = NotesAdapter(
        longClickListener = { id ->
            onShowDeleteDialog(id)
        },
        clickListener = {
            if (it.type == NoteType.NOTE_TYPE) {
                AddBirthdayDialog(
                    note = it,
                    openNote = { note -> openNote(note) },
                    saveDate = { id, date -> saveDate(id, date) }).show(parentFragmentManager, "")
            } else {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(
                        R.anim.enter_fragment,
                        R.anim.exit_fragment,
                        R.anim.enter_fragment_in,
                        R.anim.exit_fragment_out
                    )
                    .add(
                        R.id.fragment_container,
                        BirthFragment.newInstance(it)
                    )
                    .addToBackStack("")
                    .commit()
            }


        }

    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.submitUIEvent(NotesEvents.GetNotes)
        binding.recView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recView.adapter = adapter
        viewModel.viewStateObs.observe(viewLifecycleOwner) { state ->
            binding.loader.isVisible = state.isLoading
            binding.addNew.isVisible = !state.isLoading
            binding.recView.isVisible = !state.isLoading
            if (state.errorText.isNotBlank())
                Toast.makeText(context, state.errorText, Toast.LENGTH_SHORT).show()
            adapter.submitList(state.notesList)
        }
        binding.addNew.setOnClickListener {
            binding.addNote.isVisible = !binding.addNote.isVisible
            binding.addBd.isVisible = !binding.addBd.isVisible
            binding.addNew.rotation = if (binding.addNote.isVisible) 45f else 0f
        }
        binding.addNote.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.fragment_container,
                    NoteFragment.newInstance(viewModel.viewState.getEmptyNote())
                )
                .addToBackStack("")
                .commit()
        }
        binding.addBd.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.fragment_container,
                    BirthFragment.newInstance(viewModel.viewState.getEmptyBirth())
                )
                .addToBackStack("")
                .commit()
        }
        binding.deleteAll.setOnClickListener {
            onShowDeleteDialogAll()
        }
    }


    private fun onShowDeleteDialog(id: Long) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.delete_dialog_title))
            .setCancelable(true)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.submitUIEvent(NotesEvents.DeleteNote(id))
            }
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .create()
            .show()
    }

    private fun onShowDeleteDialogAll() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.delete_dialog_title))
            .setCancelable(true)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.submitUIEvent(NotesEvents.DeleteAll())
            }
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .create()
            .show()
    }

    private fun openNote(note: NoteModel) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.enter_fragment,
                R.anim.exit_fragment,
                R.anim.enter_fragment_in,
                R.anim.exit_fragment_out
            )
            .add(
                R.id.fragment_container,
                NoteFragment.newInstance(note)
            )
            .addToBackStack("")
            .commit()
    }

    fun saveDate(id: Long, s: String) {

        viewModel.submitUIEvent(NotesEvents.SaveUserDate(s, id))

    }


}



