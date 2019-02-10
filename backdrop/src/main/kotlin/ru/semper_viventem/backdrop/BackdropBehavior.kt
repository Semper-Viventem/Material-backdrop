package ru.semper_viventem.backdrop

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout


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
        private val DEFAULT_DROP_STATE = DropState.CLOSE

        private const val ARG_DROP_STATE = "arg_drop_state"
    }

    private var utils = BackdropUtils()

    private var toolbarId: Int? = null
    private var backLayoutId: Int? = null

    private var toolbar: Toolbar? = null
    private var backLayout: ViewGroup? = null
    private var frontLayout: View? = null

    private var closedIconId: Int = R.drawable.ic_menu
    private var openedIconRes: Int = R.drawable.ic_close

    private var dropState: DropState = DEFAULT_DROP_STATE

    private var dropListeners = mutableListOf<OnDropListener>()

    constructor() : super()

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onSaveInstanceState(parent: CoordinatorLayout, child: View): Parcelable {
        return Bundle().apply {
            putSerializable(ARG_DROP_STATE, dropState)
        }
    }

    override fun onRestoreInstanceState(parent: CoordinatorLayout, child: View, state: Parcelable) {
        super.onRestoreInstanceState(parent, child, state)

            dropState = (state as? Bundle)?.getSerializable(ARG_DROP_STATE) as? DropState ?: DEFAULT_DROP_STATE
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        if (toolbarId == null || backLayoutId == null) return false

        return when (dependency.id) {
            toolbarId -> true
            backLayoutId -> true
            else -> false
        }
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {

        this.frontLayout = child as? ViewGroup ?: throw IllegalArgumentException("BackLayout must extend a ViewGroup")
        when (dependency.id) {
            toolbarId -> toolbar = dependency as? Toolbar
                    ?: throw IllegalArgumentException("toolbarId doesn't match Toolbar")

            backLayoutId -> {
                backLayout = dependency as? AppBarLayout
                        ?: throw IllegalArgumentException("appBarLayoutId doesn't match AppBarLayout")
                toolbar = utils.findToolbar(backLayout!!)
                        ?: throw IllegalArgumentException("AppBarLayout mast contain a Toolbar!")
            }
        }

        if (toolbar != null && frontLayout != null) {
            initViews(parent, backLayout!!, toolbar!!, frontLayout!!)
        }

        return super.onDependentViewChanged(parent, child, dependency)
    }

    fun setOpenedIcon(@IdRes iconRes: Int) {
        this.openedIconRes = iconRes
    }

    fun setClosedIcon(@IdRes iconRes: Int) {
        this.closedIconId = iconRes
    }

    fun attachToolbar(@IdRes toolbarId: Int) {
        this.toolbarId = toolbarId
    }

    /**
     * Attach back layout to Backdrop.
     * BackDropLayout must contain a [Toolbar]
     */
    fun attachBackLayout(@IdRes appBarLayoutId: Int) {
        this.backLayoutId = appBarLayoutId
    }

    /**
     * @@deprecated — use [BackdropBehavior.attachBackLayout]
     */
    @Deprecated("Use BackdropBehavior.attachBackLayout")
    fun attachBackContainer(@IdRes backContainerId: Int) {
        this.backLayoutId = backContainerId
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
        if (backLayout != null && toolbar != null && frontLayout != null) {
            drawDropState(backLayout!!, toolbar!!, frontLayout!!, withAnimation)
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
        if (backLayout != null && toolbar != null && frontLayout != null) {
            drawDropState(backLayout!!, toolbar!!, frontLayout!!, withAnimation)
        } else {
            throw IllegalArgumentException("Toolbar and backContainer must be initialized")
        }
        notifyListeners(false)
        true
    }

    private fun initViews(parent: CoordinatorLayout, backDropLayout: View, toolbar: Toolbar, backContainer: View) {
        backContainer.y = toolbar.y + toolbar.height
        backDropLayout.layoutParams.height = parent.height - toolbar.height
        drawDropState(backDropLayout, toolbar, backContainer, false)

        with(toolbar) {
            setNavigationOnClickListener {
                dropState = when (dropState) {
                    DropState.CLOSE -> DropState.OPEN
                    DropState.OPEN -> DropState.CLOSE
                }
                drawDropState(backDropLayout, toolbar, backContainer)
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