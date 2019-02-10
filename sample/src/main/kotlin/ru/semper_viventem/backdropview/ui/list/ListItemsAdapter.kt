package ru.semper_viventem.backdropview.ui.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_list.view.*
import ru.semper_viventem.backdropview.R
import ru.semper_viventem.backdropview.inflate
import ru.semper_viventem.backdropview.load
import ru.semper_viventem.backdropview.ui.common.BaseListAdapter
import ru.semper_viventem.domain.ItemData


class ListItemsAdapter : BaseListAdapter<ItemData, ListItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, itemType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.item_list))

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), BaseListAdapter.Bindable<ItemData> {
        override fun bind(item: ItemData) {
            itemView.image.load(item.image)
            itemView.title.text = item.title
            itemView.description.text = item.description
        }
    }
}