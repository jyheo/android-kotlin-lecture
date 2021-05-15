package com.example.backgroundtask

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.*

class MyService : Service() {
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        println("MyService:onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("MyService:onStartCommand $startId")

        CoroutineScope(Dispatchers.IO).apply {
            launch {
                for (i in 1..5) {
                    println("in service $startId#$i")
                    delay(1000)
                }
                stopSelf(startId)
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        println("MyService:onDestroy")
    }

}