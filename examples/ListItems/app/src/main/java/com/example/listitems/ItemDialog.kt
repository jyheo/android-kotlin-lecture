package com.example.listitems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.example.listitems.databinding.ItemDialogLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ItemDialog(private val itemPos: Int = -1): BottomSheetDialogFragment() {
    private val viewModel by activityViewModels<MyViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) = ItemDialogLayoutBinding.inflate(inflater, container, false).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = ItemDialogLayoutBinding.bind(view)

        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, MyViewModel.icons.keys.toList())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        if (itemPos >= 0) {
            with (viewModel.items[itemPos]) {
                val i = MyViewModel.icons.keys.toList().indexOf(icon)
                binding.spinner.setSelection(i)
                binding.editTextFirstName.setText(firstName)
                binding.editTextLastName.setText(lastName)
            }
        }

        binding.buttonOk.setOnClickListener {
            val item = Item(
                    binding.spinner.selectedItem as String,
                    binding.editTextFirstName.text.toString(),
                    binding.editTextLastName.text.toString()
            )
            if (itemPos < 0)
                viewModel.addItem(item)
            else
                viewModel.updateItem(itemPos, item)
            dismiss()
        }

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

    }
}