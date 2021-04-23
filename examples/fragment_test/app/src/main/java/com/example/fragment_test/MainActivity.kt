package com.example.fragment_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.fragment_test.databinding.ActivityMainBinding

class ExampleFragment : Fragment(R.layout.example_fragment)

class ExampleFragment2 : Fragment(R.layout.example_fragment2)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAdd.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment, ExampleFragment2::class.java, null)
            }
        }

        binding.buttonReplace.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragment, ExampleFragment2::class.java, null)
            }
        }

        binding.buttonReplaceBack.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragment, ExampleFragment2::class.java, null)
                addToBackStack(null)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.viewmodel -> startActivity(Intent(this, FruitsActivity::class.java))
            R.id.navigation -> startActivity(Intent(this, NavActivity::class.java))
        }
        return true
    }
}