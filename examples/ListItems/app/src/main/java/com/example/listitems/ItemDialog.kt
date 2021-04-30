package com.example.listitems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.example.listitems.databinding.ItemDialogLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ItemDialog(val itemPos: Int = -1): BottomSheetDialogFragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) = ItemDialogLayoutBinding.inflate(inflater, container, false).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = ItemDialogLayoutBinding.bind(view)

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            listOf("person", "person outline", "person pin")
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        if (itemPos >= 0) {
            val viewModel by activityViewModels<MyViewModel>()
            with (viewModel.items[itemPos]) {
                binding.editTextFirstName.setText(firstName)
                binding.editTextLastName.setText(lastName)
            }
        }

        binding.buttonOk.setOnClickListener {
            val viewModel by activityViewModels<MyViewModel>()
            val icon: Int = when (binding.spinner.selectedItem as String) {
                "person outline" -> R.drawable.ic_baseline_person_outline_24
                "person pin" -> R.drawable.ic_baseline_person_pin_24
                else -> R.drawable.ic_baseline_person_24
            }
            if (itemPos < 0) {
                viewModel.addItem(
                    Item(
                        icon,
                        binding.editTextFirstName.text.toString(),
                        binding.editTextLastName.text.toString()
                    )
                )
            }
            dismiss()
        }

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

    }
}