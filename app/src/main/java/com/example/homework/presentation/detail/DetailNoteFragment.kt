package com.example.homework.presentation.detail

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.homework.databinding.FragmentTextBinding
import com.example.homework.presentation.model.NoteModel


class DetailNoteFragment : Fragment() {

    companion object {
        private const val KEY_NOTE = "KEY_NOTE"

        fun newInstance(note: NoteModel) = DetailNoteFragment().apply {
            arguments = bundleOf(
                KEY_NOTE to note
            )
        }
        }

    private var _binding: FragmentTextBinding? = null
    private val binding get() = _binding!!

    private val detailViewModelNote: DetailViewModelNote by lazy {
        ViewModelProvider(this)[DetailViewModelNote::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTextBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Восстанавливаем текст из liveData
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.let {
            binding.elementText.setText(detailViewModelNote.userText.value ?: "desc")
            binding.noteName.setText(detailViewModelNote.userText.value?:"name")
        }

       val currentNote: NoteModel =
          arguments?.getParcelable(KEY_NOTE) ?: NoteModel(id = 0, "", description = "")

        binding.elementText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                //Сохраняем изменения в liveData
                s?.let {
                    detailViewModelNote.submitUIEvent(DetailEvent.SaveUserText(s.toString()))
                }
            }
        })
        binding.btnBack.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .hide(this)
                .commit()
        }
    }


}


