package com.example.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

data class Student(val id: Int, val name: String)

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView1 = itemView.textView1 // calling findviewbyid
    val textView2 = itemView.textView2
    val buttonRemove = itemView.button_remove
}

class MyAdapter(private val context: Context, private val students: MutableList<Student>) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val student = students[position]

        holder.textView1.text = student.id.toString()
        holder.textView2.text = student.name
        holder.textView2.setOnClickListener {
            AlertDialog.Builder(context).setMessage("You clicked ${student.name}.").show()
        }
        holder.buttonRemove.setOnClickListener {
            students.removeAt(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return students.size
    }
}
