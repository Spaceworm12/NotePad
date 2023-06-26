package com.example.homework.presentation.SwitchTheme

import android.app.Application
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.homework.MainActivity
import com.example.homework.MainViewModel
import com.example.homework.MainViewState
import com.example.homework.R
import com.example.homework.data.models.model.app.DbNotes
import com.example.homework.databinding.FragmentSelectThemeBinding
import com.example.homework.presentation.detailBd.BirthdayEvent
import com.example.homework.presentation.model.NoteModel
import com.example.homework.presentation.model.NoteType
import com.example.homework.presentation.recycler.ListFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
private const val THEME_CODE = "THEME_CODE"

class SelectThemeFragment : Fragment() {

    private var _binding: FragmentSelectThemeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectThemeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainTheme.setOnClickListener {
            DbNotes.getSettings().edit().putInt(THEME_CODE, R.style.Theme_Homework).apply()
        }
        binding.secondTheme.setOnClickListener {
            DbNotes.getSettings().edit().putInt(THEME_CODE, R.style.Theme_Second).apply()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}