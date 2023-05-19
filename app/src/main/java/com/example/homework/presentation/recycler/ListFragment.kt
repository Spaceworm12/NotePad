package com.example.homework.presentation.recycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Database
import com.example.homework.R
import com.example.homework.data.models.model.db.Db
import com.example.homework.databinding.FragmentPreviewBinding
import com.example.homework.presentation.detail.NoteFragment
import com.example.homework.presentation.recycler.adapter.ListAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ListFragment : Fragment() {

    private var _binding: FragmentPreviewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListViewModel by lazy {
        ViewModelProvider(this)[ListViewModel::class.java]
    }

    private val adapter = ListAdapter(
        longClickListener = { id ->
            onShowDeleteDialog(id)
        },
        clickListener = {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
//                .setCustomAnimations(
//                    R.anim.enter_fragment,
//                    R.anim.exit_fragment,
//                    R.anim.enter_fragment_in,
//                    R.anim.exit_fragment_out
//                )
                .replace(
                    R.id.fragment_container,
                    NoteFragment.newInstance(it)
                )
                .addToBackStack("")
                .commit()
        }
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.submitUIEvent(ListEvent.GetNotes)
        binding.recView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recView.adapter = adapter
        viewModel.viewStateObs.observe(viewLifecycleOwner) { state ->
            binding.loader.isVisible = state.isLoading
            binding.addNote.isVisible = !state.isLoading
            binding.recView.isVisible = !state.isLoading
            if (state.errorText.isNotBlank())
                Toast.makeText(context, state.errorText, Toast.LENGTH_SHORT).show()
            adapter.submitList(state.notesList)
        }
        binding.addNote.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    NoteFragment.newInstance(viewModel.viewState.getEmptyItem())
                )
                .addToBackStack("")
                .commit()
        }
        binding.deleteAll.setOnClickListener{
            onShowDeleteDialogAll()
                }
            }

//    override fun onStart() {
//        super.onStart()
//        viewModel.submitUIEvent(ListEvent.GetNotes)
//    }



    private fun onShowDeleteDialog(id: Long) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage("Delete this note?")
            .setCancelable(true)
            .setPositiveButton("Yes") { _, _ ->
                viewModel.submitUIEvent(ListEvent.DeleteNote(id))
            }
            .setNegativeButton("No") { _, _ -> }
            .create()
            .show()
    }
    private fun onShowDeleteDialogAll() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage("Delete all notes?")
            .setCancelable(true)
            .setPositiveButton("Yes") { _, _ ->
                viewModel.submitUIEvent(ListEvent.DeleteAll())
            }
            .setNegativeButton("No") { _, _ -> }
            .create()
            .show()
    }

}



