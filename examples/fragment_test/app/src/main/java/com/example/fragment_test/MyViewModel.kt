package com.example.fragment_test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    val selectLiveData = MutableLiveData<Int>()

    init {
        selectLiveData.value = -1
    }

}