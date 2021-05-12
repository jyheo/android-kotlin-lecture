package com.example.fileexample

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import androidx.fragment.app.DialogFragment
import androidx.preference.PreferenceManager
import com.example.fileexample.databinding.ActivityMainBinding
import java.io.File

class ShowValueDialog(private val msg: String) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).apply {
            setMessage(msg)
            setPositiveButton("Ok") { _, _ -> }
        }.create()
    }
}

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MyViewModel> { MyViewModelFactory(this) }
    private val pref by lazy { getSharedPreferences("MY-SETTINGS", 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonWriteIn.setOnClickListener {
            viewModel.valueInternal = binding.editText.text.toString()
        }

        binding.buttonReadIn.setOnClickListener {
            ShowValueDialog(viewModel.valueInternal).show(supportFragmentManager, "ShowValueDialog")
        }

        binding.buttonWriteExt.setOnClickListener {
            viewModel.valueExternal = binding.editText.text.toString()

        }
        binding.buttonReadExt.setOnClickListener {
            ShowValueDialog(viewModel.valueExternal).show(supportFragmentManager, "ShowValueDialog")
        }

        binding.buttonWritePref.setOnClickListener {
            pref.edit {
                putString("key", binding.editText.text.toString())

                apply()
            }
        }

        binding.buttonReadPref.setOnClickListener {
            ShowValueDialog(pref.getString("key", "") ?: "").show(supportFragmentManager, "ShowValueDialog")
        }

        displaySettings()
    }

    private fun displaySettings() {
        val settings = PreferenceManager.getDefaultSharedPreferences(this)
        val signature = settings.getString("signature", "")
        val reply = settings.getString("reply", "")
        val sync = settings.getBoolean("sync", false)
        val attachment = settings.getBoolean("attachment", false)
        val str = """signature: $signature
reply: $reply
sync: $sync
attachment: $attachment
"""
        binding.textViewSettings.text = str
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> startActivityForResult(Intent(this, SettingsActivity::class.java), 0)
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0)
            displaySettings()
    }
}