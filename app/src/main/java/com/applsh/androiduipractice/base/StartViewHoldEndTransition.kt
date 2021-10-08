package com.applsh.androiduipractice.base

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.ViewGroup
import androidx.transition.TransitionValues
import com.applsh.androiduipractice.R

class StartViewHoldEndTransition : StartViewHoldTransition() {

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

        val previousAnimatedValue = drawingView.getTag(R.id.hold_start_view_transition_overlay_view) as? Float
        val transitionDrawable: Drawable
        val startAnim: Animator
        if (previousAnimatedValue != null) {
            duration = (previousAnimatedValue * duration).toLong()
            transitionDrawable = TransitionDrawable(pathMotion, endView, startView, endBounds, startBounds)
            transitionDrawable.setBounds(
                0,
                0,
                drawingView.width,
                drawingView.height
            )
            startAnim = ValueAnimator.ofFloat(0f, previousAnimatedValue)
            startAnim.addUpdateListener {
                Log.v("DDDD", it.animatedValue.toString())
                transitionDrawable.updateProgress(previousAnimatedValue - it.animatedValue as Float)
            }
        } else {
            transitionDrawable = TransitionDrawable(pathMotion, startView, endView, startBounds, endBounds)
            transitionDrawable.setBounds(
                0,
                0,
                drawingView.width,
                drawingView.height
            )
            startAnim = ValueAnimator.ofFloat(0f, 1f)
            startAnim.addUpdateListener {
                Log.v("DDDD", it.animatedFraction.toString())
                transitionDrawable.updateProgress(it.animatedFraction)
            }
        }

        startAnim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                endView.alpha = 0f
                drawingView.overlay.add(transitionDrawable)
            }

            override fun onAnimationEnd(animation: Animator) {
                endView.alpha = 1f
                drawingView.setTag(R.id.hold_start_view_transition_overlay_view, null)
                drawingView.overlay.remove(transitionDrawable)
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })

        return startAnim
    }
}