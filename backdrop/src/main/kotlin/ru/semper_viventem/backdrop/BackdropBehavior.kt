package ru.semper_viventem.backdrop

import android.content.Context
import android.support.annotation.IdRes
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.View


class BackdropBehavior : CoordinatorLayout.Behavior<View> {

    enum class DropState {
        OPEN,
        CLOSE
    }

    interface OnDropListener {

        fun onDrop(dropState: DropState, fromUser: Boolean)
    }

    companion object {
        private const val DEFAULT_DURATION = 300L
        private const val WITHOUT_DURATION = 0L
    }

    private var toolbarId: Int? = null
    private var backContainerId: Int? = null

    private var child: View? = null
    private var toolbar: Toolbar? = null
    private var backContainer: View? = null

    private var closedIconId: Int = R.drawable.ic_menu
    private var openedIconRes: Int = R.drawable.ic_close

    private var dropState: DropState = DropState.CLOSE

    private var dropListeners = mutableListOf<OnDropListener>()

    constructor() : super()

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        if (toolbarId == null || backContainerId == null) return false

        return when (dependency.id) {
            toolbarId -> true
            backContainerId -> true
            else -> false
        }
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {

        this.child = child
        when (dependency.id) {
            toolbarId -> toolbar = dependency as Toolbar
            backContainerId -> backContainer = dependency
        }

        if (toolbar != null && backContainer != null) {
            initViews(child, toolbar!!, backContainer!!)
        }

        return super.onDependentViewChanged(parent, child, dependency)
    }

    fun setOpenedIcon(@IdRes iconRes: Int) {
        this.openedIconRes = iconRes
    }

    fun setClosedIcon(@IdRes iconRes: Int) {
        this.closedIconId = iconRes
    }

    fun attacheToolbar(@IdRes toolbarId: Int) {
        this.toolbarId = toolbarId
    }

    fun attacheBackContainer(@IdRes backContainerId: Int) {
        this.backContainerId = backContainerId
    }

    fun addOnDropListener(listener: OnDropListener) {
        dropListeners.add(listener)
    }

    fun removeDropListener(listener: OnDropListener) {
        dropListeners.remove(listener)
    }

    fun open(withAnimation: Boolean = true): Boolean = if (dropState == DropState.OPEN) {
        false
    } else {
        dropState = DropState.OPEN
        if (child != null && toolbar != null && backContainer != null) {
            drawDropState(child!!, toolbar!!, backContainer!!, withAnimation)
        } else {
            throw IllegalArgumentException("Toolbar and backContainer must be initialized")
        }
        notifyListeners(false)
        true
    }

    fun close(withAnimation: Boolean = true): Boolean = if (dropState == DropState.CLOSE) {
        false
    } else {
        dropState = DropState.CLOSE
        if (child != null && toolbar != null && backContainer != null) {
            drawDropState(child!!, toolbar!!, backContainer!!, withAnimation)
        } else {
            throw IllegalArgumentException("Toolbar and backContainer must be initialized")
        }
        notifyListeners(false)
        true
    }

    private fun initViews(child: View, toolbar: Toolbar, backContainer: View) {
        backContainer.y = toolbar.y + toolbar.height
        drawDropState(child, toolbar, backContainer, false)

        with(toolbar) {
            setNavigationOnClickListener {
                dropState = when (dropState) {
                    DropState.CLOSE -> DropState.OPEN
                    DropState.OPEN -> DropState.CLOSE
                }
                drawDropState(child, toolbar, backContainer)
                notifyListeners(true)
            }
        }
    }

    private fun drawDropState(child: View, toolbar: Toolbar, backContainer: View, withAnimation: Boolean = true) {
        when (dropState) {
            DropState.CLOSE -> {
                drawClosedState(child, backContainer, withAnimation)
                toolbar.setNavigationIcon(closedIconId)
            }
            DropState.OPEN -> {
                drawOpenedState(child, backContainer, withAnimation)
                toolbar.setNavigationIcon(openedIconRes)
            }
        }
    }

    private fun drawClosedState(child: View, backContainer: View, withAnimation: Boolean = true) {
        val position = backContainer.y
        val duration = if (withAnimation) DEFAULT_DURATION else WITHOUT_DURATION

        child.animate().y(position).setDuration(duration).start()
    }

    private fun drawOpenedState(child: View, backContainer: View, withAnimation: Boolean = true) {
        val position = backContainer.y + backContainer.height
        val duration = if (withAnimation) DEFAULT_DURATION else WITHOUT_DURATION

        child.animate().y(position).setDuration(duration).start()
    }

    private fun notifyListeners(fromUser: Boolean) {
        dropListeners.forEach { listener ->
            listener.onDrop(dropState, fromUser)
        }
    }
}