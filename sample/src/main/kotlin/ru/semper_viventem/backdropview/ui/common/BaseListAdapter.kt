package ru.semper_viventem.backdropview.ui.common

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Easy to use base adapter for lists with single item type.
 * @author Dmitriy Gorbunov (@dmdevgo)
 */
abstract class BaseListAdapter<T, VH> : RecyclerView.Adapter<VH>() where VH : RecyclerView.ViewHolder, VH : BaseListAdapter.Bindable<T> {

    private var items: MutableList<T> = mutableListOf()

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount() = items.size

    fun getItem(position: Int) = items[position]

    fun getItems(): List<T> {
        return items
    }

    fun addItems(list: List<T>) {
        val positionStart = items.size
        items.addAll(list)
        notifyItemRangeInserted(positionStart, list.size)
    }

    fun setItems(list: List<T>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun updateItems(list: List<T>, diffCallback: DiffItemsCallback<T>) {
        if (items.isEmpty()) {
            setItems(list)
        } else {
            val diffResult = DiffUtil.calculateDiff(DiffCallback(items, list, diffCallback))
            items.clear()
            items.addAll(list)
            diffResult.dispatchUpdatesTo(this)
        }
    }

    interface Bindable<in T> {
        fun bind(item: T)
    }

    interface DiffItemsCallback<in T> {
        fun areItemsTheSame(oldItem: T, newItem: T): Boolean
        fun areContentsTheSame(oldItem: T, newItem: T): Boolean
    }

    private class DiffCallback<in T>(
            private val oldList: List<T>,
            private val newList: List<T>,
            private val diffItemsCallback: DiffItemsCallback<T>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return diffItemsCallback.areItemsTheSame(oldList[oldItemPosition], newList[newItemPosition])
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return diffItemsCallback.areContentsTheSame(oldList[oldItemPosition], newList[newItemPosition])
        }
    }
}
