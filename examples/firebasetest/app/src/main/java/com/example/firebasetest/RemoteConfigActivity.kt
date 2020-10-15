package com.example.firebasetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebasetest.databinding.ActivityRemoteConfigBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class RemoteConfigActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRemoteConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 1 // For test purpose only, 3600 seconds for production
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        binding.buttonFetchActivate.setOnClickListener {
            remoteConfig.fetchAndActivate()
                .addOnCompleteListener(this) {
                    val your_price = remoteConfig.getLong("your_price")
                    val cheat_enabled = remoteConfig.getBoolean("cheat_enabled")
                    binding.textYourPrice.text = your_price.toString()
                    binding.textCheatEnabled.text = cheat_enabled.toString()
                }
        }
    }
}