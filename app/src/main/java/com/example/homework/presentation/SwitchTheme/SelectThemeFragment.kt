package com.example.homework.presentation.SwitchTheme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.homework.R
import com.example.homework.data.models.model.app.DbNotes
import com.example.homework.databinding.FragmentThemeSelectBinding
import com.example.homework.presentation.recycler.ListFragment

private const val THEME_CODE = "THEME_CODE"
private const val SELECTED_POSITION = "SELECTED_POSITION"

class SelectThemeFragment : Fragment() {

    private var _binding: FragmentThemeSelectBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SelectThemeViewModel by lazy {
        ViewModelProvider(this)[SelectThemeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThemeSelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkIn()
        binding.mainTheme.setOnClickListener {
            viewModel.submitUIEvent(SelectThemeEvents.SwitchTheme(R.style.Theme_Homework))
            requireActivity().recreate()
        }
        binding.secondTheme.setOnClickListener {
            viewModel.submitUIEvent(SelectThemeEvents.SwitchTheme(R.style.Theme_Second))
            requireActivity().recreate()
        }
        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, ListFragment())
                .commit()
        }
    }
    private fun checkIn() {
        if (DbNotes.getSettingsTheme().getInt(THEME_CODE, 1) == R.style.Theme_Homework) {
            binding.mainTheme.isChecked = true
        } else if (DbNotes.getSettingsTheme().getInt(THEME_CODE, 1) == R.style.Theme_Second) {
            binding.secondTheme.isChecked = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}