package com.example.activity_intent

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    var count = 0
    val countLivedata: MutableLiveData<Int> = MutableLiveData<Int>()

    init {
        countLivedata.value = 0
        Log.i("MyViewModel", "MyViewModel created!")
    }

    fun increaseCount() {
        count++
        countLivedata.value = (countLivedata.value ?: 0) + 1
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("MyViewModel", "MyViewModel destroyed!")
    }
}