package com.example.chatapplication.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.chatapplication.databinding.CustomDialogBinding

class CustomDialogFragment : DialogFragment() {

    private lateinit var binding: CustomDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = CustomDialogBinding.inflate(layoutInflater)
        binding.customDialogText.text = "Hi Islam Nice Work"
        binding.customDialogButton.setOnClickListener {
            dismiss()
        }

        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
    }
}