package com.example.homework.presentation.recycler

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.homework.R
import com.example.homework.databinding.FragmentAddBirthdayDialogBinding
import com.example.homework.presentation.detail.NoteEvent
import com.example.homework.presentation.detail.NoteFragment
import com.example.homework.presentation.detail.NoteViewModel
import java.util.*

class AddBirthdayDialog : DialogFragment() {

    interface Listener {

        fun onClick()


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = FragmentAddBirthdayDialogBinding.inflate(LayoutInflater.from(context), null, false)
        val dialog: Dialog =
        AlertDialog.Builder(requireContext())
            .setCustomTitle(binding.root)
            .setCancelable(true)
            .create()
        binding.btnOpen.setOnClickListener{}
        binding.btnPicker.setOnClickListener {}
            binding.btnOpen.setOnClickListener {}
            binding.btnPicker.setOnClickListener {
                var date: String
                val d = Calendar.getInstance()
                val year = d.get(Calendar.YEAR)
                val month = d.get(Calendar.MONTH)
                val day = d.get(Calendar.DAY_OF_MONTH)
                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    { view, year, monthOfYear, dayOfMonth ->
                        date =
                            (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)

                    },
                    year,
                    month,
                    day
                )
                datePickerDialog.show()
            }
        return dialog
    }
}

//val bindingMissed =
//    DialogShopOrStorageBinding.inflate(LayoutInflater.from(context), null, false)
//
//val attentionDialog = MaterialAlertDialogBuilder(requireContext())
//    .setCustomTitle(bindingMissed.root)
//    .setCancelable(true)
//    .setBackground(ColorDrawable(ContextCompat.getColor(requireContext(), R.color.transparent)))
//    .create()
//
//bindingMissed.btOnShelf.setOnClickListener {
//    setMissedShelf(product)
//    attentionDialog.dismiss()
//}
//bindingMissed.btOnShop.setOnClickListener {
//    setMissedShop(product)
//    attentionDialog.dismiss()
//}
//
//attentionDialog.show()