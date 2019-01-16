package com.godeltech.simpleapp.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.godeltech.simpleapp.R

class SimpleRecyclerAdapter(val data: ArrayList<Pair<String, String>>) :

    RecyclerView.Adapter<SimpleRecyclerAdapter.SimpleViewHolder>() {

    class SimpleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindData(pair: Pair<String, String>) {
            (itemView.findViewById(R.id.title) as TextView).text = pair.first
            (itemView.findViewById(R.id.shortcut) as TextView).text = pair.second
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleRecyclerAdapter.SimpleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.abc_list_menu_item_layout, parent, false)
        return SimpleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.bindData(data[position])
    }

    override fun getItemCount() = data.size

}