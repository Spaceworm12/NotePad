package com.example.homework.presentation.recycler

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.homework.R
import com.example.homework.databinding.FragmentCustomBinding
import com.example.homework.databinding.FragmentNotesBinding

class Custom : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = FragmentCustomBinding.inflate(LayoutInflater.from(context), null, false)
        val dialog: Dialog =
        AlertDialog.Builder(requireContext())
            .setCustomTitle(binding.root)
            .setCancelable(true)
            .create()
        binding.btnOpen.setOnClickListener{}
        binding.btnPicker.setOnClickListener{}
        return dialog
    }
    companion object {
        const val TAG = "PurchaseConfirmationDialog"
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