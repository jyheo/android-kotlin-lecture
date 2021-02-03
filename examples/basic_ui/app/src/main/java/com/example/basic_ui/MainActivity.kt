package com.example.basic_ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.linear_layout)

        val btn = findViewById<Button>(R.id.button1)
        /*btn?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                Snackbar.make(v, "Clicked!", Snackbar.LENGTH_SHORT).show()
            }
        })*/

        btn?.setOnClickListener {
            Snackbar.make(it, "Clicked!", Snackbar.LENGTH_SHORT).show()
        }
    }
}