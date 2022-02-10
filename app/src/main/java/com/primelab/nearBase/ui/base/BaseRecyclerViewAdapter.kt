package com.primelab.nearBase.ui.base

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class BaseRecyclerViewAdapter< DATA,VH : RecyclerView.ViewHolder>(
    private val items: MutableList<DATA> = mutableListOf(),
) : RecyclerView.Adapter<VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return createViewHolderInternal(parent, viewType)
    }

    override fun getItemCount(): Int = items.size

    abstract fun createViewHolderInternal(parent: ViewGroup, viewType: Int): VH

    fun getItemAtPosition(position: Int): DATA? = items.getOrNull(position)

    fun removeItemAtPosition(position: Int): DATA? = items.removeAt(position)

    @SuppressLint("NotifyDataSetChanged")
    open fun setData(newItems: List<DATA>?) {
        this.items.clear()
        this.items.addAll(newItems ?: emptyList())
        notifyDataSetChanged()
    }
    @SuppressWarnings("UNCHECKED_CAST")
    open fun getData() : List<DATA>? {
        return this.items
    }
}