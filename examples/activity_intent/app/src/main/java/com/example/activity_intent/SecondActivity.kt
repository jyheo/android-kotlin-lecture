package com.example.activity_intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        Log.i(TAG, "$localClassName.onCreate")
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
    }
}