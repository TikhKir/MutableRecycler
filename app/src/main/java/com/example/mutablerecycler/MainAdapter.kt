package com.example.mutablerecycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.sample_item.view.*

class MainAdapter(private val buttonClickListener: OnButtonClickListener):
ListAdapter<Int, RecyclerView.ViewHolder>(IdentityDiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sample_item, parent, false)
        return SimpleViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        (holder as SimpleViewHolder).bind(data)
    }



    inner class SimpleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind (element: Int) {
            itemView.text_view_number.text = element.toString()
            itemView.button_delete.setOnClickListener { buttonClickListener.onButtonClick(element) }
        }
    }


    class IdentityDiffUtilCallback : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return true //equals anyway
        }
    }

    interface OnButtonClickListener{
        fun onButtonClick(element: Int)
    }

}