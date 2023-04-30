package com.example.homework.presentation.recycler

import android.os.Bundle
import android.system.Os.remove
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework.databinding.FragmentPreviewBinding
import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.recycler.adapter.PreviewAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class PreviewFragment : Fragment() {

    private var _binding: FragmentPreviewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecyclerViewModel by lazy {
        ViewModelProvider(this)[RecyclerViewModel::class.java]
    }

    private val adapter = PreviewAdapter { index, note ->
        onShowDeleteDialog(index, note)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreviewBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.submitUIEvent(RecyclerEvent.GetItems)
        binding.recView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recView.adapter = adapter
        viewModel.viewStateObs.observe(viewLifecycleOwner) { state ->
            binding.loader.isVisible = state.isLoading
            binding.fabAddItem.isVisible = !state.isLoading
            binding.recView.isVisible = !state.isLoading
            adapter.submitList(state.itemList)
        }


    }

    private fun onShowDeleteDialog(index: Int, note: NoteModel) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage("Delete this note?")
            .setCancelable(true)
            .setPositiveButton("Yes") { _, _ ->
                .remove(note.toString())
                adapter.notifyItemRemoved(index)
            }
            .setNegativeButton("No") { _, _ -> }
            .create()
            .show()
    }

}