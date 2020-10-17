package com.example.firebasetest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasetest.databinding.ActivityRealtimeDbBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class RealtimeDBActivity : AppCompatActivity() {
    lateinit var binding: ActivityRealtimeDbBinding
    private val database = Firebase.database
    private val itemsRef = database.getReference("items")
    private var adapter: MyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRealtimeDbBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.checkAutoID.setOnClickListener {
            binding.editID.isEnabled = !binding.checkAutoID.isChecked
            if (!binding.editID.isEnabled)
                binding.editID.setText("")
        }

        // recyclerview setup
        binding.recyclerViewItems.layoutManager = LinearLayoutManager(this)
        adapter = MyAdapter(this, emptyList())
        adapter?.setOnItemClickListener {
            queryItem(it)
        }
        binding.recyclerViewItems.adapter = adapter

        binding.buttonAddUpdate.setOnClickListener {
            addItem()
        }

        binding.buttonUpdatePrice.setOnClickListener {
            updatePrice()
        }

        binding.buttonDelete.setOnClickListener {
            deleteItem()
        }

        binding.buttonIncrPrice.setOnClickListener {
            incrPrice()
        }

        itemsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val items = mutableListOf<Item>()
                for (child in dataSnapshot.children) {
                    items.add(Item(child.key ?: "", child.value as Map<*, *>))
                }
                adapter?.updateList(items)
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })

        val query = itemsRef.orderByChild("price")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (child in dataSnapshot.children) {
                    println("${child.key} - ${child.value}")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
    }

    private fun addItem() {
        val name = binding.editItemName.text.toString()
        if (name.isEmpty()) {
            Snackbar.make(binding.root, "Input name!", Snackbar.LENGTH_SHORT).show()
            return
        }
        val price = binding.editPrice.text.toString().toInt()
        val autoID = binding.checkAutoID.isChecked
        val itemID = binding.editID.text.toString()
        if (!autoID and itemID.isEmpty()) {
            Snackbar.make(binding.root, "Input ID or check Auto-generate ID!", Snackbar.LENGTH_SHORT).show()
            return
        }
        val itemMap = hashMapOf(
            "name" to name,
            "price" to price
        )
        if (autoID) {
            val itemRef = itemsRef.push()
            itemRef.setValue(itemMap)
        } else {
            val itemRef = itemsRef.child(itemID)
            itemRef.setValue(itemMap)
        }
    }

    private fun queryItem(itemID: String) {
        itemsRef.child(itemID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val map = dataSnapshot.value as Map<*, *>
                binding.editID.setText(itemID)
                binding.checkAutoID.isChecked = false
                binding.editID.isEnabled = true
                binding.editItemName.setText(map["name"].toString())
                binding.editPrice.setText(map["price"].toString())
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
    }

    private fun updatePrice() {
        val itemID = binding.editID.text.toString()
        val price = binding.editPrice.text.toString().toInt()
        if (itemID.isEmpty()) {
            Snackbar.make(binding.root, "Input ID!", Snackbar.LENGTH_SHORT).show()
            return
        }
        itemsRef.child(itemID).child("price").setValue(price)
            .addOnSuccessListener { queryItem(itemID) }

        // or

        /*
        val itemMap = hashMapOf(
            "price" to price
        )
        itemsRef.child(itemID).updateChildren(itemMap as Map<String, Any>)
            .addOnSuccessListener { queryItem(itemID) }

        */
    }

    private fun deleteItem() {
        val itemID = binding.editID.text.toString()
        if (itemID.isEmpty()) {
            Snackbar.make(binding.root, "Input ID!", Snackbar.LENGTH_SHORT).show()
            return
        }
        itemsRef.child(itemID).removeValue()
            .addOnSuccessListener {  }
    }

    private fun incrPrice() {
        val itemID = binding.editID.text.toString()
        if (itemID.isEmpty()) {
            Snackbar.make(binding.root, "Input ID!", Snackbar.LENGTH_SHORT).show()
            return
        }

        itemsRef.child(itemID).child("price")
            .runTransaction(object : Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                var p = mutableData.value.toString().toIntOrNull() ?: 0
                p++
                mutableData.value = p
                return Transaction.success(mutableData)
            }

            override fun onComplete(
                databaseError: DatabaseError?,
                committed: Boolean,
                currentData: DataSnapshot?
            ) {
                // Transaction completed
                queryItem(itemID)
            }
        })
    }
}