package ru.semper_viventem.backdropview

import android.content.Context
import android.support.annotation.IdRes
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.view.*


class BackdropBehavior : CoordinatorLayout.Behavior<CardView> {

    enum class DropState {
        OPEN,
        CLOSE
    }

    companion object {
        private const val DEFAULT_DURATION = 300L
        private const val WITHOUT_DURATION = 0L
    }

    private var toolbarId: Int? = null
    private var backContainerId: Int? = null

    private var toolbar: Toolbar? = null
    private var backContainer: View? = null

    private var closedIconId: Int = R.drawable.ic_menu
    private var openedIconId: Int = R.drawable.ic_close

    private var dropState: DropState = DropState.CLOSE

    constructor() : super()

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun layoutDependsOn(parent: CoordinatorLayout, child: CardView, dependency: View): Boolean {
        if (toolbarId == null || backContainerId == null) return false

        return when (dependency.id) {
            toolbarId -> true
            backContainerId -> true
            else -> false
        }
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: CardView, dependency: View): Boolean {

        when (dependency.id) {
            toolbarId -> toolbar = dependency as Toolbar
            backContainerId -> backContainer = dependency
        }

        if (toolbar != null && backContainer != null) {
            initViews(child, toolbar!!, backContainer!!)
        }

        return super.onDependentViewChanged(parent, child, dependency)
    }

    fun attacheToolbar(@IdRes toolbarId: Int) {
        this.toolbarId = toolbarId
    }

    fun attacheBackContainer(@IdRes backContainerId: Int) {
        this.backContainerId = backContainerId
    }

    private fun initViews(child: CardView, toolbar: Toolbar, backContainer: View) {
        backContainer.y = toolbar.y + toolbar.height
        close(child, backContainer, false)

        with(toolbar) {
            setNavigationIcon(closedIconId)
            setNavigationOnClickListener {
                when(dropState) {
                    DropState.CLOSE -> {
                        open(child, backContainer)
                        setNavigationIcon(openedIconId)
                    }
                    DropState.OPEN -> {
                        close(child, backContainer)
                        setNavigationIcon(closedIconId)
                    }
                }
            }
        }
    }

    private fun close(child: CardView, backContainer: View, withAnimation: Boolean = true) {
        val position = backContainer.y
        val duration = if (withAnimation) DEFAULT_DURATION else WITHOUT_DURATION

        child.animate().y(position).setDuration(duration).start()
        dropState = DropState.CLOSE
    }

    private fun open(child: CardView, backContainer: View, withAnimation: Boolean = true) {
        val position = backContainer.y + backContainer.height
        val duration = if (withAnimation) DEFAULT_DURATION else WITHOUT_DURATION

        child.animate().y(position).setDuration(duration).start()
        dropState = DropState.OPEN
    }
}