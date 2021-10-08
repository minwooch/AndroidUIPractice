package com.applsh.androiduipractice.base

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.RectF
import android.util.Log
import android.view.ViewGroup
import androidx.transition.TransitionValues
import com.applsh.androiduipractice.R

class StartViewHoldStartTransition : StartViewHoldTransition() {

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        if (startValues == null || endValues == null) return null

        val startView = startValues.view
        val endView = endValues.view

        val startMatrix = startValues.values[PROP_GLOBAL_MATRIX] as Matrix
        val endMatrix = endValues.values[PROP_GLOBAL_MATRIX] as Matrix

        // 부모에 대해 상대적인 위치
        val startBounds = RectF(startValues.values[PROP_BOUNDS] as Rect)
        val endBounds = RectF(endValues.values[PROP_BOUNDS] as Rect)

        val drawingView = findAncestorById(endView, drawingViewId) as ViewGroup

        // drawingView에 대한 상대 좌표로 변환할 수 있는 matrix
        val sceneStartMatrix = Matrix(startMatrix)
        val sceneEndMatrix = Matrix(endMatrix)
        drawingView.transformMatrixToLocal(sceneStartMatrix)
        drawingView.transformMatrixToLocal(sceneEndMatrix)

        // bounds를 drawingView에 대한 상대 좌표로 변환
        sceneStartMatrix.mapRect(startBounds)
        sceneEndMatrix.mapRect(endBounds)

        val transitionDrawable =
            TransitionDrawable(pathMotion, startView, endView, startBounds, endBounds)
        transitionDrawable.setBounds(
            0,
            0,
            drawingView.width,
            drawingView.height
        )
        val startAnim = ValueAnimator.ofFloat(0f, 1f)
        startAnim.addUpdateListener {
            Log.v("DDDD", it.animatedFraction.toString())
            drawingView.setTag(R.id.hold_start_view_transition_overlay_view, it.animatedFraction)
            transitionDrawable.updateProgress(it.animatedFraction)
        }

        startAnim.addListener(object : Animator.AnimatorListener {

            var canceled = false

            override fun onAnimationStart(animation: Animator) {
                endView.alpha = 0f
                drawingView.overlay.add(transitionDrawable)
            }

            override fun onAnimationEnd(animation: Animator) {
                endView.alpha = 1f
                if (!canceled) {
                    drawingView.setTag(R.id.hold_start_view_transition_overlay_view, null)
                }
                drawingView.overlay.remove(transitionDrawable)
            }

            override fun onAnimationCancel(animation: Animator) {
                canceled = true
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })

        return startAnim
    }
}