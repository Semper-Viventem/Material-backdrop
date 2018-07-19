package ru.semper_viventem.backdropview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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
    }

    override fun onBackPressed() {
        if (!backdropBehavior.close()) {
            finish()
        }
    }
}
