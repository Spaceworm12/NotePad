package com.example.homework.presentation.recycler

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.homework.R
import com.example.homework.databinding.FragmentAddBirthdayDialogBinding
import com.example.homework.presentation.model.NoteModel
import java.util.*

class AddBirthdayDialog(
    private val note: NoteModel,
    private val openNote: (NoteModel) -> Unit,
    private val saveDate: (Long, String) -> Unit
) : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val binding =
            FragmentAddBirthdayDialogBinding.inflate(LayoutInflater.from(context), null, false)
        val dialog: Dialog =
            AlertDialog.Builder(requireContext())
                .setCustomTitle(binding.root)
                .setCancelable(true)
                .create()

        binding.btnOpen.setOnClickListener {
            openNote.invoke(note)
            dismiss()
        }


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
                    saveDate.invoke(note.id, date)
                    dismiss()

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
