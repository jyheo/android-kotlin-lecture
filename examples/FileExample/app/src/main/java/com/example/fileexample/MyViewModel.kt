package com.example.fileexample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.io.File
import java.lang.IllegalArgumentException

class MyViewModel(private val file: File) : ViewModel() {
    var value: String = readValue()
        set(v) {
            field = v
            writeValue(v)
        }

    private fun readValue() : String {
        return file.readText(Charsets.UTF_8)
    }

    private fun writeValue(value: String) {
        file.writeText(value, Charsets.UTF_8)
    }
}

class MyViewModelFatory(private val file: File) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MyViewModel::class.java))
            MyViewModel(file) as T
        else
            throw IllegalArgumentException()
    }
}