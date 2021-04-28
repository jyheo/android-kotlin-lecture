package com.example.navigationui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.navigationui.databinding.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val myViewModel: MyViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        myViewModel.nameLiveData.observe(viewLifecycleOwner) {
            binding.textViewName.text = it
        }
        // popup menu
        binding.textViewPopup.setOnClickListener {
            showPopup(it)
        }
    }

    private fun showPopup(v: View) {
        PopupMenu(requireContext(), v).apply {
            inflate(R.menu.nav_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.homeFragment -> {
                        Snackbar.make(v, "HomeFragment", Snackbar.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
        }.show()
    }
}

class Page1Fragment : Fragment(R.layout.fragment_page1) {
    private val myViewModel: MyViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPage1Binding.bind(view)
        myViewModel.nameLiveData.observe(viewLifecycleOwner) {
            binding.textViewName.text = it
        }
    }
}

class Page2Fragment : Fragment(R.layout.fragment_page2) {
    private val myViewModel: MyViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPage2Binding.bind(view)
        myViewModel.nameLiveData.observe(viewLifecycleOwner) {
            binding.textViewName.text = it
        }
    }
}

class Page3Fragment : Fragment(R.layout.fragment_page3) {
    private val myViewModel: MyViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPage3Binding.bind(view)
        myViewModel.nameLiveData.observe(viewLifecycleOwner) {
            binding.textViewName.text = it
        }
    }
}

class OkCancelDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        return AlertDialog.Builder(requireActivity()).apply {
            setMessage("OK-CANCEL Dialog")
            setPositiveButton("OK") { dialog, id -> println("OK")}
            setNegativeButton("CANCEL") { dialog, id -> println("CANCEL")}
        }.create()
    }
}

class MyBottomSheetDialog : BottomSheetDialogFragment() {
    private val myViewModel: MyViewModel by activityViewModels()

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
            myViewModel.nameLiveData.value = binding.editTextName.text.toString()
            dismiss()
        }

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

    }
}