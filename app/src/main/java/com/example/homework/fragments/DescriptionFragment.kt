package com.example.homework.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.homework.databinding.FragmentTextBinding
import com.example.homework.data.models.model.noteModel.NoteModel


class DescriptionFragment : Fragment() {
    private var _binding: FragmentTextBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val KEY_NOTE = "KEY_NOTE"

        fun newInstance(note: NoteModel) = DescriptionFragment().apply {
            arguments = bundleOf(
                KEY_NOTE to note
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTextBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


        val currentNote: NoteModel =
            arguments?.getParcelable(KEY_NOTE) ?: NoteModel(id = 0, "Нихера", description = "")

        binding.elementText.setText(currentNote.description)
        binding.btnBack.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .hide(this)
                .commit()
        }

    }


}

