package com.example.homework.presentation.recycler

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework.R
import com.example.homework.databinding.FragmentListNotesBinding
import com.example.homework.presentation.selectTheme.SelectThemeFragment
import com.example.homework.presentation.detail.NoteFragment
import com.example.homework.presentation.detailBd.BirthdayFragment
import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.model.NoteType
import com.example.homework.presentation.recycler.adapter.ListAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ListFragment : Fragment() {

    private var _binding: FragmentListNotesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListViewModel by lazy {
        ViewModelProvider(this)[ListViewModel::class.java]
    }

    private val adapter = ListAdapter(
        longClickListener = { id ->
            onShowDeleteDialog(id)
        },
        clickListener = {
            if (it.type == NoteType.NOTE_TYPE) {
                BirthdayDyalog(
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
                        BirthdayFragment.newInstance(it)
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
        _binding = FragmentListNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.submitUIEvent(ListEvents.GetNotes)
        binding.recView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recView.adapter = adapter
        viewModel.viewStateObs.observe(viewLifecycleOwner) { state ->
            setElements(state)
        }
        setButtonsClicks()
        setHasOptionsMenu(true)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.context_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                parentFragmentManager.beginTransaction().add(
                    R.id.fragment_container,
                    SelectThemeFragment()
                )
                    .addToBackStack("")
                    .commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setButtonsClicks() {
        binding.addNew.setOnClickListener {
            setVisibilityElements()
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
                    BirthdayFragment.newInstance(viewModel.viewState.getEmptyBirth())
                )
                .addToBackStack("")
                .commit()
        }
        binding.deleteAll.setOnClickListener {
            onShowDeleteDialogAll()
        }
    }

    private fun setVisibilityElements() {
        binding.addNote.isVisible = !binding.addNote.isVisible
        binding.addBd.isVisible = !binding.addBd.isVisible
        binding.addNew.rotation = if (binding.addNote.isVisible) 45f else 0f
    }

    private fun setElements(state: ListViewState) {
        binding.loader.isVisible = state.isLoading
        binding.addNew.isVisible = !state.isLoading
        binding.recView.isVisible = !state.isLoading
        if (state.errorText.isNotBlank())
            Toast.makeText(context, state.errorText, Toast.LENGTH_SHORT).show()
        adapter.submitList(state.notesList)
    }


    private fun onShowDeleteDialog(id: Long) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.delete_dialog_title))
            .setCancelable(true)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.submitUIEvent(ListEvents.DeleteNote(id))
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
                viewModel.submitUIEvent(ListEvents.DeleteAll)
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

        viewModel.submitUIEvent(ListEvents.SaveUserDate(s, id))

    }


}



