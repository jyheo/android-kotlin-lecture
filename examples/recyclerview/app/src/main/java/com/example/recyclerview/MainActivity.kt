package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val students = mutableListOf(Student(1, "john"), Student(2, "tom"))
    private val myAdapter = MyAdapter(this, students)
    private var stdID = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = myAdapter

        binding.buttonAdd.setOnClickListener {
            students.add(0, Student(stdID, "test $stdID"))
            stdID++
            myAdapter.notifyItemInserted(0)
        }
    }
}