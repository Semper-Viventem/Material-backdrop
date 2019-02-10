package ru.semper_viventem.backdropview.ui.gallery

import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import kotlinx.android.synthetic.main.screen_gallery.view.*
import ru.semper_viventem.backdropview.R
import ru.semper_viventem.backdropview.ui.common.Screen
import ru.semper_viventem.domain.ItemsFactory


class GalleryScreen : Screen() {

    companion object {
        private const val SNAP_COUNT = 2
    }

    override val layoutId: Int = R.layout.screen_gallery

    private val galleryAdapter = GalleryAdapter()
    private val snapHelper = LinearSnapHelper()

    override fun onInitView(view: View) {

        with(view.recyclerView) {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, SNAP_COUNT, GridLayoutManager.HORIZONTAL, false)
            adapter = galleryAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL).apply {
                this.setDrawable(context.getDrawable(R.drawable.divider_empty))
            })
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                this.setDrawable(context.getDrawable(R.drawable.divider_empty))
            })
        }
        with(snapHelper) {
            attachToRecyclerView(view.recyclerView)
        }

        galleryAdapter.setItems(ItemsFactory.getDefault())
    }
}