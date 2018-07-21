package ru.semper_viventem.backdropview.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.semper_viventem.backdrop.BackdropBehavior
import ru.semper_viventem.backdropview.R
import ru.semper_viventem.backdropview.findBehavior
import ru.semper_viventem.backdropview.ui.gallery.GalleryScreen
import ru.semper_viventem.backdropview.ui.list.ListScreen
import ru.semper_viventem.backdropview.ui.text.TextScreen

class MainActivity : AppCompatActivity() {

    companion object {
        private val MENU_GALLERY = R.id.menuGallery
        private val MENU_TEXT = R.id.menuText
        private val MENU_LIST = R.id.menuList

        private val FRAGMENT_CONTAINER = R.id.foregroundContainer
    }

    private lateinit var backdropBehavior: BackdropBehavior

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        backdropBehavior = foregroundContainer.findBehavior()
        with(backdropBehavior) {
            attacheBackContainer(R.id.backContainer)
            attacheToolbar(R.id.toolbar)
        }
        with(toolbar) {
            setTitle(R.string.app_name)
        }

        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                MENU_GALLERY -> showPage(GalleryScreen())
                MENU_TEXT -> showPage(TextScreen())
                MENU_LIST -> showPage(ListScreen())
            }
            backdropBehavior.close()
            true
        }
    }

    override fun onBackPressed() {
        if (!backdropBehavior.close()) {
            finish()
        }
    }

    private fun showPage(page: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(FRAGMENT_CONTAINER, page)
            .commit()
    }
}
