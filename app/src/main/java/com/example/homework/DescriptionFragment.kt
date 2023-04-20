package com.example.homework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
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

        val currentNote: Note =
            arguments?.getParcelable(KEY_NOTE) ?: Note(id = 0, "Нихера", description = "")

        binding.elementText.text= currentNote.description
    }


}

