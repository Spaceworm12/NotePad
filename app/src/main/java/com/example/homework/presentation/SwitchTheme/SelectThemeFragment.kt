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

private const val THEME_CODE = "THEME_CODE"

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
        binding.mainTheme.isChecked = statecurrentTheme == R.style.Theme_Homework
        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.mainTheme.setOnClickListener {
            DbNotes.getSettings().edit().putInt(THEME_CODE, R.style.Theme_Homework).apply()
     viewModel.submitUIEvent(SelectThemeEvent.CheckTheme)
            requireActivity().recreate()
        }
        binding.secondTheme.setOnClickListener {
            DbNotes.getSettings().edit().putInt(THEME_CODE, R.style.Theme_Second).apply()
//            viewModel.submitUIEvent(SelectThemeEvent.CheckTheme)
            requireActivity().recreate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}