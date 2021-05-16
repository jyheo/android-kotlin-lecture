package com.example.backgroundtask

import android.app.*
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.*

class MyService : Service() {
    private val binder = LocalBinder()

    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService() = this@MyService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    var startedCount = 0
        private set

    override fun onCreate() {
        super.onCreate()
        println("MyService:onCreate")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Android 8.0
            createNotificationChannel()
        }
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("MyService:onStartCommand $startId")
        startedCount++

        startForeground(notificationID, createNotification())

        CoroutineScope(Dispatchers.IO + Job()).apply {
            launch {
                for (i in 1..10) {
                    println("in service $startId#$i")
                    updateNotification(notificationID, createNotification(i*10))
                    delay(1000)
                }
                stopSelf(startId)
            }
        }

        return super.onStartCommand(intent, flags, startId) // START_STICKY, NOT_STICKY, REDELIVERY_INTENT
    }

    override fun onDestroy() {
        super.onDestroy()
        println("MyService:onDestroy")
    }

    private val channelID = "default"
    private val notificationID = 1

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(channelID, "default channel",
            NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = "description text of this channel."
        NotificationManagerCompat.from(this).createNotificationChannel(channel)
    }

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

}