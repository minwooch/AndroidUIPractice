package com.applsh.androiduipractice.base

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.transition.PathMotion
import androidx.transition.Transition
import androidx.transition.TransitionValues

class HoldStartViewTransition : Transition() {

    private val PROP_BOUNDS = "holdStartViewTransition:bounds"
    private val PROP_GLOBAL_MATRIX = "holdStartViewTransition:globalMatrix"

    private val TRANSITION_PROPS = arrayOf(
        PROP_BOUNDS,
        PROP_GLOBAL_MATRIX
    )

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

    private fun findAncestorById(view: View, @IdRes ancestorId: Int): View {
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

        val drawingBaseView = if (endView.parent != null) endView else startView
        val drawingView = findAncestorById(drawingBaseView, android.R.id.content)

        // 부모에 대해 상대적인 위치
        val startBounds = RectF(startValues.values[PROP_BOUNDS] as Rect)
        val endBounds = RectF(endValues.values[PROP_BOUNDS] as Rect)

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


        val startAnim = ReverseAnimationOnCancelAnimator()
        startAnim.setFloatValues(0f, 1f)

        startAnim.addUpdateListener {
            transitionDrawable.updateProgress(it.animatedFraction)
        }

        val anim = AnimatorSet().apply {
            play(startAnim)
                .apply {

                }
        }
        addListener(object : TransitionListener {
            override fun onTransitionPause(transition: Transition) {
            }

            override fun onTransitionResume(transition: Transition) {
            }

            override fun onTransitionStart(transition: Transition) {
                endView.alpha = 0f
                drawingView.overlay.add(transitionDrawable)
            }

            override fun onTransitionEnd(transition: Transition) {
                drawingView.overlay.remove(transitionDrawable)
                transition.removeListener(this)
                endView.alpha = 1f
            }

            override fun onTransitionCancel(transition: Transition) {
                val bp = ""
            }
        })

        val endViewParent = endView.parent
        if (endViewParent is ViewGroup) {
            endViewParent.suppressLayout(true)
            addListener(object : TransitionListener {
                var canceled = false

                override fun onTransitionStart(transition: Transition) {
                }

                override fun onTransitionEnd(transition: Transition) {
                    if (!canceled)
                        endViewParent.suppressLayout(false)
                    transition.removeListener(this)
                }

                override fun onTransitionCancel(transition: Transition) {
                    endViewParent.suppressLayout(false)
                    canceled = true

                }
                override fun onTransitionPause(transition: Transition) {
                    endViewParent.suppressLayout(false)
                }

                override fun onTransitionResume(transition: Transition) {
                    endViewParent.suppressLayout(true)
                }
            })
        }
        return anim
    }

    class TransitionDrawable(
        private val pathMotion: PathMotion,
        private val startView: View,
        private val endView: View,
        private val startBounds: RectF,
        private val endBounds: RectF,
    ) : Drawable() {

        private val pathMeasure: PathMeasure
        private val pathPosition = FloatArray(2)
        private val pathLength: Float

        private val currentStartBounds = RectF(startBounds)
        private val currentEndBounds = RectF(startBounds)

        private var startScale: Float = 1f

        init {
            val startPoint = PointF(startBounds.centerX(), startBounds.top)
            val endPoint = PointF(endBounds.centerX(), endBounds.top)
            val path = pathMotion.getPath(startPoint.x, startPoint.y, endPoint.x, endPoint.y)
            pathMeasure = PathMeasure(path, false)
            pathLength = pathMeasure.length

            pathPosition[0] = startBounds.centerX()
            pathPosition[1] = startBounds.top
        }

        val testPaint = Paint().apply {
            color = 0xFF000000.toInt()
        }

        override fun draw(
            canvas: Canvas
        ) {
            val checkpoint = canvas.save()
            val rectF = RectF(
                0f,
                0f,
                startView.width.toFloat(),
                startView.height.toFloat()
            )
            canvas.translate(currentStartBounds.left, currentStartBounds.top)
            canvas.scale(startScale, startScale)
            // canvas.drawRect(rectF, testPaint)
            startView.draw(canvas)
            canvas.restoreToCount(checkpoint)
        }

        override fun setAlpha(alpha: Int) {
        }

        override fun setColorFilter(colorFilter: ColorFilter?) {
        }

        override fun getOpacity(): Int {
            return PixelFormat.TRANSLUCENT
        }

        fun updateProgress(progress: Float) {
            pathMeasure.getPosTan(pathLength * progress, pathPosition, null)

            val motionPathX = pathPosition[0]
            val motionPathY = pathPosition[1]

            val startWidth = startBounds.width()
            val endWidth = endBounds.width()
            val startHeight = startBounds.height()
            val endHeight = endBounds.height()

            val currentWidth = calcMid(startWidth, endWidth, progress)
            startScale = currentWidth / startWidth
            val endScale = currentWidth / endWidth
            val currentStartHeight = startHeight * startScale
            val currentEndHeight = endHeight * endScale

            currentStartBounds.set(
                motionPathX - currentWidth / 2,
                motionPathY,
                motionPathX + currentWidth / 2,
                motionPathY + currentStartHeight
            )
            currentEndBounds.set(
                motionPathX - currentWidth / 2,
                motionPathY,
                motionPathX + currentWidth / 2,
                motionPathY + currentEndHeight
            )
            invalidateSelf()
        }

        private fun calcMid(start: Float, end: Float, progress: Float): Float {
            return start + (end - start) * progress
        }
    }
}

class ReverseAnimationOnCancelAnimator : ValueAnimator()