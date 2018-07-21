package ru.semper_viventem.backdropview.ui.common

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


abstract class Screen : Fragment {

    constructor(): super()

    constructor(args: Bundle) : super() {
        arguments = args
    }

    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onStart() {
        super.onStart()

        onInitView(view!!)
    }

    abstract fun onInitView(view: View)
}