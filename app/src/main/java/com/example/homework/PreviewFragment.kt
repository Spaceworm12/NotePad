package com.example.homework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework.databinding.FragmentPreviewBinding
import com.example.notepad.Adapter


class PreviewFragment : Fragment() {

    private var _binding: FragmentPreviewBinding? = null
    private val binding get() = _binding!!

    private val adapter = Adapter { note ->
        requireContext()
        parentFragmentManager.apply {
            this.beginTransaction()
                .setCustomAnimations(
                    R.anim.enter_fragment,
                    R.anim.exit_fragment,
                    R.anim.enter_fragment_in,
                    R.anim.exit_fragment_out
                )
            .add(
            R.id.fragment_container,
            DescriptionFragment.newInstance(note)
        )
            .addToBackStack("")
            .commit()
        }
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
        val allNotes = listOf(
            Note(id = 1, name = "Купить хлеба", description = "Описание первого"),
            Note(id = 2, name = "Сходить в магазин", description = "Описание второго"),
            Note(id = 3, name = "Покормить попугая", description = "Описание третьего"),
            Note(id = 4, name = "Сделать гимнастику", description = "Описание четвертого"),
            Note(id = 5, name = "Посмотреть график", description = "Описание пятого"),
        )
        binding.recView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recView.adapter = adapter
        adapter.submitList(allNotes)


    }
}