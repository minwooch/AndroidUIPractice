package com.applsh.androiduipractice.base

import android.graphics.Matrix
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.transition.Transition
import androidx.transition.TransitionValues

abstract class StartViewHoldTransition : Transition() {
    protected val PROP_BOUNDS = "holdStartViewTransition:bounds"
    protected val PROP_GLOBAL_MATRIX = "holdStartViewTransition:globalMatrix"

    private val TRANSITION_PROPS = arrayOf(
        PROP_BOUNDS,
        PROP_GLOBAL_MATRIX
    )

    @IdRes
    var drawingViewId = android.R.id.content

    /**
     * 상대 좌표를 구하기 위해 부모의 global matrix 를 계산
     */
    private fun captureValues(transitionValues: TransitionValues) {
        val view = transitionValues.view

        transitionValues.values[PROP_BOUNDS] = Rect(
            view.left, view.top,
            view.right, view.bottom
        )

        val parent = view.parent as ViewGroup
        val parentMatrix = Matrix()
        parent.transformMatrixToGlobal(parentMatrix)
        parentMatrix.preTranslate(-parent.scrollX.toFloat(), -parent.scrollY.toFloat())
        transitionValues.values[PROP_GLOBAL_MATRIX] = parentMatrix
    }

    override fun getTransitionProperties(): Array<String>? {
        return TRANSITION_PROPS
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    internal fun findAncestorById(view: View, @IdRes ancestorId: Int): View {
        val resourceName = view.resources.getResourceName(ancestorId)
        var curView: View? = view
        while (curView != null) {
            if (curView.id == ancestorId) {
                return curView
            }
            val parent = curView.parent
            if (parent is View) {
                curView = parent
            } else {
                break
            }
        }
        throw IllegalArgumentException("$resourceName is not a valid ancestor")
    }
}