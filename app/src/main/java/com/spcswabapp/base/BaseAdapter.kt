package com.spcswabapp.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T , VB : ViewBinding> : RecyclerView.Adapter<BaseAdapter<T, VB>.ViewHolder>() {

    var list : ArrayList<T> = ArrayList()
    @SuppressLint("NotifyDataSetChanged")
    set(value) {
        notifyDataSetChanged()
        field = value
    }

    abstract fun bindingInflater(layoutInflater: LayoutInflater, parent: ViewGroup, attachParent: Boolean) : VB

    abstract fun onItemViewReady(binding : VB, item : T, position: Int)

    var onItemClickListener : ((T) -> Unit) ?= null
    var onItemClickListenerPos : ((T,Int) -> Unit) ?= null
    inner class ViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(bindingInflater(
        LayoutInflater.from(parent.context),parent,false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        onItemViewReady(holder.binding,list[position],position)

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(list[position])
            onItemClickListenerPos?.invoke(list[position],position)
        }
    }

    override fun getItemCount() = list.size
}