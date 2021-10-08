package com.applsh.androiduipractice.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BottomSheetCoverBehavior : CoordinatorLayout.Behavior<View> {
    constructor() : super()
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private fun isBottomSheet(view: View): Boolean {
        val layoutParams = view.layoutParams
        return if (layoutParams is CoordinatorLayout.LayoutParams) {
            layoutParams.behavior is BottomSheetBehavior
        } else false
    }

    private fun updateAlphaForBottomSheet(bottomSheet: View, child: View): Boolean {
        val bottomSheetLayoutParams = bottomSheet.layoutParams as CoordinatorLayout.LayoutParams
        if (bottomSheet.top < child.bottom + bottomSheetLayoutParams.topMargin) {
            val diff = bottomSheet.top - child.top
            if (diff < 0) return false
            child.alpha = diff.toFloat() / child.height
            return true
        } else {
            if (child.alpha != 1.0f) {
                child.alpha = 1.0f
                return true
            }
            return false
        }
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
            return updateAlphaForBottomSheet(dependency, child)
        } else {
            return false
        }
    }
}

