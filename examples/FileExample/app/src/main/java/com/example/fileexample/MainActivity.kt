package com.example.fileexample

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.fileexample.databinding.ActivityMainBinding
import java.io.File

class ShowValueDialog(private val msg: String) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).apply {
            setMessage(msg)
            setPositiveButton("Ok") { _, _ -> }
        }.create()
    }
}

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val vmInternal by viewModels<MyViewModel> {
        MyViewModelFatory(File(filesDir, "appfile.txt"))
    }

    private val vmExternal by viewModels<MyViewModel> {
        MyViewModelFatory(File(getExternalFilesDir(null), "appfile.txt"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonWriteIn.setOnClickListener {
            vmInternal.value = binding.editText.text.toString()
        }

        binding.buttonReadIn.setOnClickListener {
            ShowValueDialog(vmInternal.value).show(supportFragmentManager, "ShowValueDialog")
        }

        binding.buttonWriteExt.setOnClickListener {
            vmExternal.value = binding.editText.text.toString()

        }
        binding.buttonReadExt.setOnClickListener {
            ShowValueDialog(vmExternal.value).show(supportFragmentManager, "ShowValueDialog")
        }
    }

    private val isExternalStorageMounted: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state
        }
}