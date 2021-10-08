package com.applsh.androiduipractice.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BottomSheetFloatingBehavior : CoordinatorLayout.Behavior<View> {

    private val DEFAULT_MARGIN = 32

    private val margin: Int

    constructor() : super() {
        margin = DEFAULT_MARGIN
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        margin = DEFAULT_MARGIN
    }

    private fun isBottomSheet(view: View): Boolean {
        val layoutParams = view.layoutParams
        return if (layoutParams is CoordinatorLayout.LayoutParams) {
            layoutParams.behavior is BottomSheetBehavior
        } else false
    }

    private fun updatePositionForBottomSheet(bottomSheet: View, child: View): Boolean {
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.halfExpandedRatio
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN || bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
            val y = bottomSheet.y
            child.y = y - (child.height)
            val bottomSheetLayoutParams = bottomSheet.layoutParams as CoordinatorLayout.LayoutParams
            val childLayoutParams = child.layoutParams
        } else {

        }
        return true
    }

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return isBottomSheet(dependency)
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (isBottomSheet(dependency)) {
            return updatePositionForBottomSheet(dependency, child)
        } else {
            return false
        }
    }
}