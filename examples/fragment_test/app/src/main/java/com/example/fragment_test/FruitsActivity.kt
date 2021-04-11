package com.example.fragment_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels


class ListFruitsFragment : Fragment(R.layout.list_fruits) {
    private val viewModel: MyViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.selectLiveData.value = checkedId
        }

    }
}

class DetailFruitFragment : Fragment(R.layout.detail_fruit) {
    private val viewModel: MyViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectLiveData.observe(viewLifecycleOwner) {
            val tv = view.findViewById<TextView>(R.id.textViewName)
            tv.text = "$it"
        }
    }
}

class FruitsActivity : AppCompatActivity() {
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fruits)

        viewModel.selectLiveData.observe(this) {
            //title = "Selected: $it"   // change title
            supportActionBar?.title = "Selected: $it"   // change title
        }
    }
}