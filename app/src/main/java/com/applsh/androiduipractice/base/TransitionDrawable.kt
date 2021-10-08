package com.applsh.androiduipractice.base

import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.View
import androidx.transition.PathMotion

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
    private val currentEndBounds = RectF(endBounds)

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

    override fun draw(
        canvas: Canvas
    ) {
        val checkpoint = canvas.save()
        canvas.translate(currentStartBounds.left, currentStartBounds.top)
        canvas.scale(startScale, startScale)
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