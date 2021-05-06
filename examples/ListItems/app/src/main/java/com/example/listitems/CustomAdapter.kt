package com.example.listitems

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listitems.databinding.ItemLayoutBinding

class CustomAdapter(private val viewModel: MyViewModel) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setContents(pos: Int) {
            with (viewModel.items[pos]) {
                binding.imageView.setImageResource(MyViewModel.icons[icon] ?: R.drawable.ic_baseline_person_24)
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

    // ViewHolder 생성, ViewHolder 는 View 를 담는 상자
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemLayoutBinding.inflate(layoutInflater, viewGroup, false)
        return ViewHolder(binding)
    }

    // ViewHolder 에 데이터 쓰기
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setContents(position)
    }

    override fun getItemCount() = viewModel.items.size
}