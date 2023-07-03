package com.example.homework.presentation.selecttheme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.homework.R
import com.example.homework.databinding.FragmentThemeSelectBinding
import com.example.homework.presentation.recycler.ListFragment

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
        viewModel.viewStateObs.observe(viewLifecycleOwner) { state ->
            setElements(state.currentTheme)
        }
        binding.firstTheme.setOnClickListener {
            viewModel.submitUIEvent(SelectThemeEvents.SwitchTheme(R.style.Theme_Homework))
            requireActivity().recreate()
        }
        binding.secondTheme.setOnClickListener {
            viewModel.submitUIEvent(SelectThemeEvents.SwitchTheme(R.style.Theme_Second))
            requireActivity().recreate()
        }
        binding.thirdTheme.setOnClickListener {
            viewModel.submitUIEvent(SelectThemeEvents.SwitchTheme(R.style.Theme_Third))
            requireActivity().recreate()
        }

        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, ListFragment())
                .commit()
        }
    }

    private fun setElements(currentTheme: Int) {
        binding.firstTheme.isChecked = currentTheme == R.style.Theme_Homework
        binding.secondTheme.isChecked = currentTheme == R.style.Theme_Second
        binding.thirdTheme.isChecked = currentTheme == R.style.Theme_Third
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}