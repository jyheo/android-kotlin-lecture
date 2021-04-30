package com.example.listitems

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.listitems.databinding.ItemLayoutBinding

data class Item(val image: Int, val firstName: String, val lastName: String)

class CustomAdapter(private val viewModel: MyViewModel) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setContents(pos: Int) {
            with (viewModel.items[pos]) {
                binding.imageView.setImageResource(image)
                binding.textView.text = firstName
                binding.textView2.text = lastName
            }
            binding.root.setOnClickListener {
                viewModel.itemClickEvent.value = pos
            }
            binding.root.setOnLongClickListener {
                viewModel.itemLongClick = pos
                false // for context menu
            }
        }
    }

    // ViewHolder 생성, ViewHolder는 View를 담는 상자
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(viewGroup.context),
            viewGroup, false))
    }

    // ViewHolder에 데이터 쓰기
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setContents(position)
    }

    override fun getItemCount() = viewModel.items.size
}