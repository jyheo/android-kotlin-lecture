package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val students = mutableListOf(Student(1, "john"), Student(2, "tom"))
    private val myAdapter = MyAdapter(this, students)
    private var stdID = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = myAdapter

        button_add.setOnClickListener {
            students.add(0, Student(stdID, "test $stdID"))
            stdID++
            myAdapter.notifyItemInserted(0)
        }
    }
}