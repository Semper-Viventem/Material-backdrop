package ru.semper_viventem.backdropview.ui

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import ru.semper_viventem.backdrop.BackdropBehavior
import ru.semper_viventem.backdropview.R
import ru.semper_viventem.backdropview.findBehavior
import ru.semper_viventem.backdropview.ui.gallery.GalleryScreen
import ru.semper_viventem.backdropview.ui.list.ListScreen
import ru.semper_viventem.backdropview.ui.text.TextScreen

class MainActivity : AppCompatActivity() {

    companion object {
        private const val ARG_LAST_MENU_ITEM = "last_menu_item"

        private const val MENU_GALLERY = R.id.menuGallery
        private const val MENU_TEXT = R.id.menuText
        private const val MENU_LIST = R.id.menuList

        private const val FRAGMENT_CONTAINER = R.id.foregroundContainer

        private const val DEFAULT_ITEM = MENU_GALLERY
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
            checkMenuPosition(item.itemId)
            backdropBehavior.close()
            true
        }

        val currentItem = savedInstanceState?.getInt(ARG_LAST_MENU_ITEM) ?: DEFAULT_ITEM
        navigationView.setCheckedItem(currentItem)
        checkMenuPosition(navigationView.checkedItem!!.itemId)
    }

    override fun onBackPressed() {
        if (!backdropBehavior.close()) {
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(ARG_LAST_MENU_ITEM, navigationView.checkedItem!!.itemId)

        super.onSaveInstanceState(outState)
    }

    private fun checkMenuPosition(@IdRes menuItemId: Int) {
        when (menuItemId) {
            MENU_GALLERY -> showPage(GalleryScreen())
            MENU_TEXT -> showPage(TextScreen())
            MENU_LIST -> showPage(ListScreen())
        }
    }

    private fun showPage(page: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(FRAGMENT_CONTAINER, page)
            .commit()
    }
}
