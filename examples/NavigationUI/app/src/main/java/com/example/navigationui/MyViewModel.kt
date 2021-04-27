package com.example.navigationui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    val nameLiveData = MutableLiveData<String>()

    init {
        nameLiveData.value = ""
    }
}