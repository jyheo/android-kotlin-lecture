package com.example.activity_intent

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.activity_intent.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MyViewModel
    private lateinit var binding: ActivityMainBinding
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.i(TAG, "$localClassName.onCreate")

        binding.buttonSecondActivity.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        binding.buttonDialActivity.setOnClickListener {
            val implicitIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:114"))
            startActivity(implicitIntent)
        }

        binding.buttonThirdActivity.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            intent.putExtra("UserDefinedExtra", "Hello")
            startActivityForResult(intent, request_code)
        }

        // ViewModel
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        binding.textViewCount.text = getString(R.string.count_in_activity, count)
        binding.textViewCountViewmodel.text = getString(R.string.count_in_ViewModel, viewModel.count)
        viewModel.countLivedata.observe(this) {
            binding.textViewLivedata.text = getString(R.string.count_in_ViewModel_LiveData, it)
        }

        binding.buttonIncr.setOnClickListener {
            count++
            viewModel.increaseCount()
            binding.textViewCount.text = getString(R.string.count_in_activity, count)
            binding.textViewCountViewmodel.text = getString(R.string.count_in_ViewModel, viewModel.count)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == request_code) {
            val msg = data?.getStringExtra("ResultString") ?: ""
            Snackbar.make(binding.root, "ActivityResult:$resultCode $msg", Snackbar.LENGTH_SHORT).show()
            Log.i(TAG, "ActivityResult:$resultCode $msg")
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "$localClassName.onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "$localClassName.onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "$localClassName.onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "$localClassName.onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "$localClassName.onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "$localClassName.onDestroy")
    }

    companion object {
        private const val TAG = "ActivityLifeCycle"
        private const val request_code = 0
    }
}