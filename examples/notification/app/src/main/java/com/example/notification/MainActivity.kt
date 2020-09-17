package com.example.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            //showNotification()
            //showNotificationBigText()
            //showNotificationBigPicture()
            //showNotificationProgress()
            showNotificationButton()
        }
    }

    private val channelID = "default"

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Android 8.0
            val channel = NotificationChannel(
                    channelID, "default channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "description text of this channel."
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private val myNotificationID = 1

    private fun showNotification() {
        val builder = NotificationCompat.Builder(this, channelID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notification Title")
                .setContentText("Notification body")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        NotificationManagerCompat.from(this)
                .notify(myNotificationID, builder.build())
    }

    private fun showNotificationBigText() {
        val builder = NotificationCompat.Builder(this, channelID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notification Title")
                .setContentText("Notification body")
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText(resources.getString(R.string.long_notification_body)))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        NotificationManagerCompat.from(this)
                .notify(myNotificationID, builder.build())
    }

    private fun showNotificationBigPicture() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.android_hsu)
        val builder = NotificationCompat.Builder(this, channelID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(bitmap)
                .setContentTitle("Notification Title")
                .setContentText("Notification body")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)
                        .bigLargeIcon(null))  // hide largeIcon while expanding
        NotificationManagerCompat.from(this)
                .notify(myNotificationID, builder.build())
    }

    private fun showNotificationButton() {
        val intent = Intent(this, TestActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val builder = NotificationCompat.Builder(this, channelID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notification Title")
                .setContentText("Notification body")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.android_hsu, "Action", pendingIntent)
        NotificationManagerCompat.from(this)
                .notify(myNotificationID, builder.build())
    }

    private fun showNotificationProgress() {
        val builder = NotificationCompat.Builder(this, channelID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Progress")
                .setContentText("In progress")
                .setProgress(100, 0, false)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        NotificationManagerCompat.from(this)
                .notify(myNotificationID, builder.build())

        Thread {
            for (i in (1..100).step(10)) {
                Thread.sleep(1000)
                builder.setProgress(100, i, false)
                NotificationManagerCompat.from(this)
                        .notify(myNotificationID, builder.build())
            }
            builder.setContentText("Completed")
                    .setProgress(0, 0, false)
            NotificationManagerCompat.from(this)
                    .notify(myNotificationID, builder.build())
        }.start()
    }
}
