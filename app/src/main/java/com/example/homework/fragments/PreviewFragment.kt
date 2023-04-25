package com.example.homework.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework.R
import com.example.homework.databinding.FragmentPreviewBinding
import com.example.homework.fragments.adapterAndHolder.PreviewAdapter
import com.example.homework.models.NoteModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class PreviewFragment : Fragment() {

    private var _binding: FragmentPreviewBinding? = null
    private val binding get() = _binding!!

    private val allNotes = mutableListOf<NoteModel>()

    private val adapter = PreviewAdapter(
        clickListener = { note ->
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
        },
        longClickListener = { index, note ->
            onShowDeleteDialog(index, note)
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
        allNotes.add(NoteModel(id = 1, name = "Купить хлеба", description = "Описание первого"))
        allNotes.add(
            NoteModel(
                id = 2,
                name = "Сходить в магазин",
                description = "Описание второго"
            )
        )
        allNotes.add(
            NoteModel(
                id = 3,
                name = "Покормить попугая",
                description = "Описание третьего"
            )
        )
        allNotes.add(
            NoteModel(
                id = 4,
                name = "Сделать гимнастику",
                description = "Описание четвертого"
            )
        )
        allNotes.add(NoteModel(id = 5, name = "Посмотреть график", description = "Описание пятого"))
        allNotes.add(NoteModel(id = 6, name = "Купить хлеба", description = "Описание шестого"))
        allNotes.add(
            NoteModel(
                id = 7,
                name = "Сходить в магазин",
                description = "Описание седьмого"
            )
        )
        allNotes.add(
            NoteModel(
                id = 8,
                name = "Покормить попугая",
                description = "Описание восьмого"
            )
        )
        allNotes.add(
            NoteModel(
                id = 9,
                name = "Сделать гимнастику",
                description = "Описание девятого"
            )
        )
        allNotes.add(
            NoteModel(
                id = 10,
                name = "Посмотреть график",
                description = "Описание десятого"
            )
        )
        allNotes.add(
            NoteModel(
                id = 11,
                name = "Купить хлеба",
                description = "Описание одиннадцатого"
            )
        )
        allNotes.add(
            NoteModel(
                id = 12,
                name = "Сходить в магазин",
                description = "Описание двенадцатого"
            )
        )
        allNotes.add(
            NoteModel(
                id = 13,
                name = "Покормить попугая",
                description = "Описание тринадцатого"
            )
        )
        allNotes.add(
            NoteModel(
                id = 14,
                name = "Сделать гимнастику",
                description = "Описание четырнадцатого"
            )
        )
        allNotes.add(
            NoteModel(
                id = 15,
                name = "Посмотреть график",
                description = "Описание пятнадцатого"
            )
        )

        binding.recView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recView.adapter = adapter
        adapter.submitList(allNotes)


    }

    private fun onShowDeleteDialog(index: Int, note: NoteModel) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage("Delete this note?")
            .setCancelable(true)
            .setPositiveButton("Yes") { _, _ ->
                allNotes.remove(note)
                adapter.notifyItemRemoved(index)
            }
            .setNegativeButton("No") { _, _ -> }
            .create()
            .show()
    }

}