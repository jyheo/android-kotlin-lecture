package com.example.backgroundtask

import android.app.Notification
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.backgroundtask.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var scope: CoroutineScope

    val channelID = "default"

    private fun updateNotification(id: Int, notification: Notification) {
        NotificationManagerCompat.from(this).notify(id, notification)
    }

    private fun createNotification(progress: Int = 0) = NotificationCompat.Builder(this, channelID)
        .setContentTitle("Downloading")
        .setContentText("Downloading a file from a cloud")
        .setSmallIcon(R.drawable.ic_baseline_cloud_download_24)
        .setOnlyAlertOnce(true)  // importance 에 따라 알림 소리가 날 때, 처음에만 소리나게 함
        .setProgress(100, progress, false)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonStartCo.setOnClickListener {
            startCoroutine()

        }

        binding.buttonStopCo.setOnClickListener {
            stopCoroutine()
        }

        binding.buttonStartService.setOnClickListener {
            Intent(this, MyService::class.java).also {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Android 8.0
                    startForegroundService(it) // must call startForground in a few seconds.
                } else {
                    startService(it)
                }
            }
        }

        binding.buttonStartedCount.setOnClickListener {
            binding.textViewCount.text = "${myService?.startedCount}"
        }

    }

    private fun startCoroutine() {
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
                    updateNotification(1, createNotification(i*10))
                    delay(1000)
                    println("#$i")
                }
            }
        }
    }

    private fun stopCoroutine() = scope.cancel()

    private var myService: MyService? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            myService = (service as MyService.LocalBinder).getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            myService = null
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, MyService::class.java).also {
            bindService(it, serviceConnection, BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(serviceConnection)
    }
}
