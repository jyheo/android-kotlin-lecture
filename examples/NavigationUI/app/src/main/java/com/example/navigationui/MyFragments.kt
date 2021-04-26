package com.example.navigationui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.navigationui.databinding.MyBottomDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeFragment : Fragment(R.layout.fragment_home)

class Page1Fragment : Fragment(R.layout.fragment_page1)

class Page2Fragment : Fragment(R.layout.fragment_page2)

class Page3Fragment : Fragment(R.layout.fragment_page3)

class OkCancelDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        return with (AlertDialog.Builder(requireActivity())) {
            setMessage("OK-CANCEL Dialog")
            setPositiveButton("OK") { dialog, id -> println("OK")}
            setNegativeButton("CANCEL") { dialog, id -> println("CANCEL")}
            create()
        }
    }
}

class MyBottomSheetDialog : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        isCancelable = false
        return MyBottomDialogBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = MyBottomDialogBinding.bind(view)
        binding.buttonOk.setOnClickListener {
            dismiss()
        }

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

    }
}