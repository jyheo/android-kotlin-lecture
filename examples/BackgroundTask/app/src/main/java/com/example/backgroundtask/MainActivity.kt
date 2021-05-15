package com.example.backgroundtask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.backgroundtask.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var scope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonStartCo.setOnClickListener {
            scope = CoroutineScope(Dispatchers.Default + Job()).apply {
                launch {
                    for (i in 1..10) {
                        delay(1000)
                        withContext(Dispatchers.Main) {
                            binding.textView.text = "$i"
                        }
                    }
                }

                launch {
                    for (i in 1..10) {
                        delay(1000)
                        println("#$i")
                    }
                }
            }
        }

        binding.buttonStopCo.setOnClickListener {
            scope.cancel()
        }

        binding.button.setOnClickListener {
            Snackbar.make(it, "TEST", Snackbar.LENGTH_SHORT).show()
        }

        binding.buttonStartService.setOnClickListener {
            Intent(this, MyService::class.java).also {
                startService(it)
            }
        }

        binding.buttonStopService.setOnClickListener {
            stopService(Intent(this, MyService::class.java))
        }

    }
}