package com.applsh.androiduipractice.base

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView


class DisableNestedScrollView : NestedScrollView {
    private var scrollable = true

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return scrollable && super.onInterceptTouchEvent(ev)
    }

    fun setScrollingEnabled(enabled: Boolean) {
        scrollable = enabled
    }

    fun dd () {
        val f = this.javaClass.superclass.getDeclaredField("mIsBeingDragged")
        f.isAccessible = true
        f.get(this)
    }
}