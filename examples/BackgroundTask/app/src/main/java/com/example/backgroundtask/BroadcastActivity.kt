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


class MyDialogFragment(val msg: String, val showButton: Boolean = false,
                       val listener: DialogInterface.OnClickListener? = null) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return (AlertDialog.Builder(requireContext())).apply {
            setTitle("Alert")
            setMessage(msg)
            if (showButton) {
                setPositiveButton("Yes", listener)
                setNegativeButton("No") { _, _ -> }
            }
        }.create()
    }
}

class BroadcastActivity : AppCompatActivity() {
    val binding by lazy { ActivityBroadcastBinding.inflate(layoutInflater) }

    private val broadcastReceiver = MyBroadcastReceiver()
    private var permissionReadSMS = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonSendMyBroad.setOnClickListener {
            sendBroadcast(Intent(ACTION_MY_BROADCAST))
        }

        checkPermission()
    }

    fun MyDialogFragment.show() {
        show(supportFragmentManager, "")
    }

    private fun checkPermission() {
        val permission = "android.permission.RECEIVE_SMS"

        val requestPermLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it)  // it : isGranted
                permissionReadSMS = true
            else
                MyDialogFragment(getString(R.string.no_perm_read_sms)).show() // supportFragmentManager, "")
        }

        when {
            checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED -> {
                permissionReadSMS = true
            }
            shouldShowRequestPermissionRationale(permission) -> {
                MyDialogFragment(getString(R.string.req_perm_read_sms_reason), true) {
                        _, _ -> requestPermLauncher.launch(permission)
                }.show(supportFragmentManager, "")
            }
            else -> {
                requestPermLauncher.launch(permission)
            }
        }
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