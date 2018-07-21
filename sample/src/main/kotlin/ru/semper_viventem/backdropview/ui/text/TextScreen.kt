package ru.semper_viventem.backdropview.ui.text

import android.view.View
import kotlinx.android.synthetic.main.screen_text.view.*
import ru.semper_viventem.backdropview.R
import ru.semper_viventem.backdropview.load
import ru.semper_viventem.backdropview.ui.common.Screen


class TextScreen : Screen() {

    override val layoutId: Int = R.layout.screen_text

    override fun onInitView(view: View) {

        view.image.load("http://mariakucherenko.com/wp-content/uploads/2011/08/20110814_%D0%BA%D0%BE%D1%82.jpg")
    }
}