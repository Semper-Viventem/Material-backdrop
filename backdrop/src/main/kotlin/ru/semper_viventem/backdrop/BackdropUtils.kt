package ru.semper_viventem.backdrop

import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar

class BackdropUtils {

    fun findToolbar(viewGroup: ViewGroup): Toolbar? {
        for (chileId in 0..viewGroup.childCount) {
            val childView = viewGroup.getChildAt(chileId)
            if (childView is Toolbar) {
                return childView
            }
        }

        return null
    }
}