package com.example.listitems

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    val itemsListData = MutableLiveData<ArrayList<Item>>()
    val items = ArrayList<Item>()

    val itemClickEvent = MutableLiveData<Int>()
    var itemLongClick = -1

    init {
        items.add(Item(R.drawable.ic_baseline_person_24, "Yuh-jung", "Youn"))
        items.add(Item(R.drawable.ic_baseline_person_24, "Steven", "Yeun"))
        items.add(Item(R.drawable.ic_baseline_person_24, "Alan", "Kim"))
        items.add(Item(R.drawable.ic_baseline_person_24, "Ye-ri", "Han"))
        items.add(Item(R.drawable.ic_baseline_person_24, "Noel", "Cho"))
        items.add(Item(R.drawable.ic_baseline_person_24, "Lee Issac", "Chung"))
        itemsListData.value = items
    }

    fun addItem(item: Item) {
        items.add(item)
        itemsListData.value = items
    }
}