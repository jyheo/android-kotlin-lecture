package com.example.backgroundtask

import android.app.Dialog
import android.content.*
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.backgroundtask.databinding.ActivityBroadcastBinding
import com.google.android.material.snackbar.Snackbar

class BroadcastActivity : AppCompatActivity() {
    val binding by lazy { ActivityBroadcastBinding.inflate(layoutInflater) }

    private val broadcastReceiver = MyBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonSendMyBroad.setOnClickListener {
            sendBroadcast(Intent(ACTION_MY_BROADCAST))
        }

        checkPermission("RECEIVE_SMS", true)
    }

    private fun checkPermission(perm: String, requestPerm: Boolean = false): Boolean {
        val permission = "android.permission.${perm}"

        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)
            return true

        if (!requestPerm)
            return false

        val requestPermLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it == false) { // permission is not granted!
                AlertDialog.Builder(this).apply {
                    setTitle("Warning")
                    setMessage(getString(R.string.no_permission, perm))
                }.show()
            }
        }

        if (shouldShowRequestPermissionRationale(permission)) {
            // you should explain the reason why this app needs the permission.
            AlertDialog.Builder(this).apply {
                setTitle("Reason")
                setMessage(getString(R.string.req_permission_reason, perm))
                setPositiveButton("Allow") { _, _ -> requestPermLauncher.launch(permission) }
                setNegativeButton("Deny") { _, _ -> }
            }.show()
        } else {
            // should be called in onCreate()
            requestPermLauncher.launch(permission)
        }

        return false
    }

    private fun startBroadcastReceiver() {
        IntentFilter().also {
            it.addAction(Intent.ACTION_POWER_CONNECTED)
            it.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            it.addAction("android.provider.Telephony.SMS_RECEIVED")
            it.addAction(ACTION_MY_BROADCAST)
            registerReceiver(broadcastReceiver, it)
        }

    }

    override fun onStart() {
        super.onStart()
        startBroadcastReceiver()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

    inner class MyBroadcastReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                Intent.ACTION_POWER_CONNECTED -> { showBroadcast(Intent.ACTION_POWER_CONNECTED) }
                ACTION_MY_BROADCAST -> { showBroadcast(ACTION_MY_BROADCAST) }
                else -> { showBroadcast(intent?.action ?: "NO ACTION")}
            }
        }

        private fun showBroadcast(msg: String) {
            println(msg)
            Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val ACTION_MY_BROADCAST = "ACTION_MY_BROADCAST"
    }
}