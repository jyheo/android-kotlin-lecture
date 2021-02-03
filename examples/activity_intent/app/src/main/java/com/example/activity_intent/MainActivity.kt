package com.example.activity_intent

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "$localClassName.onCreate")
        var btn = findViewById<Button>(R.id.buttonSecondActivity)
        btn.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
        btn = findViewById<Button>(R.id.buttonDialActivity)
        btn.setOnClickListener {
            val implicitIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:114"))
            startActivity(implicitIntent)
        }
        btn = findViewById<Button>(R.id.buttonThirdActivity)
        btn.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            intent.putExtra("UserDefinedExtra", "Hello")
            startActivityForResult(intent, request_code)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != request_code || data == null) return
        val msg = data.getStringExtra("ResultString") ?: ""
        Snackbar.make(findViewById(R.id.buttonThirdActivity), "ActivityResult:$resultCode $msg", Snackbar.LENGTH_SHORT).show()
        Log.i(TAG, "ActivityResult:$resultCode $msg")
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