package com.example.prog_ui

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.prog_ui.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.scaleType = ImageView.ScaleType.CENTER

        binding.button.setOnClickListener {
            // hide softkeyboard
            (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(it.windowToken, 0)

            val pet = "Dog:${binding.radioDog.isChecked}, Cat:${binding.radioCat.isChecked}"
            binding.textView2.text = binding.editTextTextPersonName.text
            Snackbar.make(it, pet, Snackbar.LENGTH_SHORT).show()
        }
    }

}