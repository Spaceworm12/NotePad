package com.example.homework

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework.databinding.FragmentDescriptionBinding
import com.example.homework.databinding.FragmentPreviewBinding
import com.example.homework.databinding.FragmentTextBinding


class DescriptionFragment : Fragment() {
    private var _binding: FragmentTextBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val KEY_NOTE = "KEY_NOTE"

        fun newInstance(note: Note) = DescriptionFragment().apply {
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

        val currentNote: Note = arguments?.getParcelable(KEY_NOTE) ?: Note(id = 0, "Нихера", description = "")

        binding.elementText.text = currentNote.description
    }
}

