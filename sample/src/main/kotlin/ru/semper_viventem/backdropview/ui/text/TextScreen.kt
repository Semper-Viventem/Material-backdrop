package ru.semper_viventem.backdropview.ui.text

import android.view.View
import kotlinx.android.synthetic.main.screen_text.view.*
import ru.semper_viventem.backdropview.R
import ru.semper_viventem.backdropview.load
import ru.semper_viventem.backdropview.ui.common.Screen


class TextScreen : Screen() {

    override val layoutId: Int = R.layout.screen_text

    override fun onInitView(view: View) {

        view.image.load("https://habrastorage.org/webt/r9/p8/fi/r9p8figkbszo_cyhdg-6nad4mp0.png")
    }
}