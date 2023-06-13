//package com.example.homework.presentation.recycler
//
//import android.app.AlertDialog
//import android.app.DatePickerDialog
//import android.app.Dialog
//import android.os.Bundle
//import androidx.fragment.app.DialogFragment
//import com.example.homework.R
//import com.example.homework.databinding.FragmentCustomBinding
//import com.example.homework.presentation.detail.NoteFragment
//import java.util.*
//
//class CustomFragment: DialogFragment() {
//
//    private var _binding: FragmentCustomBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateDialog(savedInstanceState: Bundle?) {
//        val builder = AlertDialog.Builder(context)
//        val inflater = requireActivity().layoutInflater
//        builder.setView(inflater.inflate(R.layout.fragment_custom, null))
//        binding.selectedDate.setText("")
//        binding.btnPicker.setOnClickListener {
//            binding.btnPicker.setOnClickListener {
//                val d = Calendar.getInstance()
//                val year = d.get(Calendar.YEAR)
//                val month = d.get(Calendar.MONTH)
//                val day = d.get(Calendar.DAY_OF_MONTH)
//                val datePickerDialog = DatePickerDialog(
//                    requireContext(),
//                    { view, year, monthOfYear, dayOfMonth ->
//                        binding.selectedDate.text =
//                            (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
//                    },
//                    year,
//                    month,
//                    day
//                )
//                datePickerDialog.show()
//                binding.selectedDate.setText("")
//            }
//        }
//        binding.btnOpen.setOnClickListener {
//            requireActivity()
//                .supportFragmentManager
//                .beginTransaction()
//                .setCustomAnimations(
//                    R.anim.enter_fragment,
//                    R.anim.exit_fragment,
//                    R.anim.enter_fragment_in,
//                    R.anim.exit_fragment_out
//                )
//                .add(
//                    R.id.fragment_container,
//                    NoteFragment.newInstance(it)
//                )
//                .addToBackStack("")
//                .commit()
//        }
//
//        AlertDialog.Builder(requireContext())
//            .setMessage(getString(R.string.app_name_notepad))
//            .create()
//    }
//
//
//    companion object {
//        const val TAG = "PurchaseConfirmationDialog"
//    }
//}