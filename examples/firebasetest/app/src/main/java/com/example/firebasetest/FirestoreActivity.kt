package com.example.firebasetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasetest.databinding.ActivityFirestoreBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirestoreBinding
    private var adapter: MyAdapter? = null
    private val db: FirebaseFirestore = Firebase.firestore
    private val itemsCollectionRef = db.collection("items")
    private var snapshotListener: ListenerRegistration? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirestoreBinding.inflate(layoutInflater)
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

        updateList()  // list items on recyclerview

        binding.buttonAddUpdate.setOnClickListener {
            addItem()
        }

        binding.buttonUpdatePrice.setOnClickListener {
            updatePrice()
        }

        binding.buttonIncrPrice.setOnClickListener {
            incrPrice()
        }

        binding.buttonQuery.setOnClickListener {
            queryWhere()
        }

        binding.buttonDelete.setOnClickListener {
            deleteItem()
        }
    }

    override fun onStart() {
        super.onStart()

        // snapshot listener for all items
        snapshotListener = itemsCollectionRef.addSnapshotListener { snapshot, error ->
            binding.textSnapshotListener.text = StringBuilder().apply {
                for (doc in snapshot!!.documentChanges) {
                    append("${doc.type} ${doc.document.id} ${doc.document.data}")
                }
            }
        }
        // sanpshot listener for single item
        /*
        itemsCollectionRef.document("1").addSnapshotListener { snapshot, error ->
            Log.d(TAG, "${snapshot?.id} ${snapshot?.data}")
        }*/
    }

    override fun onStop() {
        super.onStop()
        snapshotListener?.remove()
    }

    private fun updateList() {
        itemsCollectionRef.get().addOnSuccessListener {
            val items = mutableListOf<Item>()
            for (doc in it) {
                items.add(Item(doc))
            }
            adapter?.updateList(items)
        }
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
            itemsCollectionRef.add(itemMap)
                .addOnSuccessListener { updateList() }.addOnFailureListener {  }
        } else {
            itemsCollectionRef.document(itemID).set(itemMap)
                .addOnSuccessListener { updateList() }.addOnFailureListener {  }
        }
    }

    private fun queryItem(itemID: String) {
        itemsCollectionRef.document(itemID).get()
            .addOnSuccessListener {
                binding.editID.setText(it.id)
                binding.checkAutoID.isChecked = false
                binding.editID.isEnabled = true
                binding.editItemName.setText(it["name"].toString())
                binding.editPrice.setText(it["price"].toString())
            }.addOnFailureListener {
            }
    }

    private fun updatePrice() {
        val itemID = binding.editID.text.toString()
        val price = binding.editPrice.text.toString().toInt()
        if (itemID.isEmpty()) {
            Snackbar.make(binding.root, "Input ID!", Snackbar.LENGTH_SHORT).show()
            return
        }
        itemsCollectionRef.document(itemID).update("price", price)
            .addOnSuccessListener { queryItem(itemID) }
    }

    private fun incrPrice() {
        val itemID = binding.editID.text.toString()
        if (itemID.isEmpty()) {
            Snackbar.make(binding.root, "Input ID!", Snackbar.LENGTH_SHORT).show()
            return
        }

        db.runTransaction {
            val docRef = itemsCollectionRef.document(itemID)
            val snapshot = it.get(docRef)
            var price = snapshot.getLong("price") ?: 0
            price += 1
            it.update(docRef, "price", price)
        }
            .addOnSuccessListener { queryItem(itemID) }
    }

    private fun queryWhere() {
        val p = 100
        binding.progressWait.visibility = View.VISIBLE
        itemsCollectionRef.whereLessThan("price", p).get()
            .addOnSuccessListener {
                binding.progressWait.visibility = View.GONE
                val items = arrayListOf<String>()
                for (doc in it) {
                    items.add("${doc["name"]} - ${doc["price"]}")
                }
                AlertDialog.Builder(this)
                    .setTitle("Items which price less than $p")
                    .setItems(items.toTypedArray(), { dialog, which ->  }).show()
            }
            .addOnFailureListener {
                binding.progressWait.visibility = View.GONE
            }
    }

    private fun deleteItem() {
        val itemID = binding.editID.text.toString()
        if (itemID.isEmpty()) {
            Snackbar.make(binding.root, "Input ID!", Snackbar.LENGTH_SHORT).show()
            return
        }
        itemsCollectionRef.document(itemID).delete()
            .addOnSuccessListener { updateList() }
    }

    companion object {
        const val TAG = "FirestoreActivity"
    }
}
