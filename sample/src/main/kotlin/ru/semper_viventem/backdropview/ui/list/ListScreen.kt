package ru.semper_viventem.backdropview.ui.list

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.screen_list.view.*
import ru.semper_viventem.backdropview.R
import ru.semper_viventem.backdropview.ui.common.Screen
import ru.semper_viventem.domain.ItemsFactory


class ListScreen : Screen() {

    override val layoutId: Int = R.layout.screen_list

    private val listAdapter = ListItemsAdapter()

    override fun onInitView(view: View) {
        with(view.recyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }

        listAdapter.setItems(ItemsFactory.getDefault())
    }
}