package com.applsh.androiduipractice.base

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout


class GradientDrawable : Drawable() {
    private val rect = Rect()
    private val paint = Paint()
    private val matrix = Matrix()
    private val valueAnimatorUpdateListenerAdapter = ValueAnimator.AnimatorUpdateListener {
        invalidateSelf()
    }

    var valueAnimator: ValueAnimator? = null
        set(value) {
            stopAnimation()
            if (value != null) {
                value.addUpdateListener(valueAnimatorUpdateListenerAdapter)
                value.repeatCount = ValueAnimator.INFINITE
            }
            field = value
        }

    init {
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
    }

    fun startAnimation() {
        valueAnimator?.start()
    }

    fun stopAnimation() {
        valueAnimator?.end()
        valueAnimator?.removeAllUpdateListeners()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

    override fun setAlpha(alpha: Int) {
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun onBoundsChange(bounds: Rect?) {
        super.onBoundsChange(bounds)
        if (bounds != null) {
            rect.set(bounds)
            paint.shader = LinearGradient(
                0f, 0f, bounds.width().toFloat(), 0f, intArrayOf(
                    0x7FFFFFFF,
                    -1,
                    -1,
                    0x7FFFFFFF
                ), floatArrayOf(0f, 0.24995f, 0.25005f, 0.5f), Shader.TileMode.CLAMP
            )
        }
    }

    override fun draw(canvas: Canvas) {
        val va = valueAnimator
        if (va != null) {
            matrix.reset()
            matrix.postTranslate(rect.width() * (2 * va.animatedFraction - 1), 0f)
            paint.shader.setLocalMatrix(matrix)
            canvas.drawRect(rect, paint)
        } else {
            canvas.drawRect(rect, paint)
        }
    }
}


class LoadingPlaceHolderConstraintLayout : ConstraintLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val gradientDrawable = GradientDrawable()

    init {
        setWillNotDraw(false)
        setLayerType(LAYER_TYPE_HARDWARE, null)
        gradientDrawable.callback = this
        gradientDrawable.valueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 2000
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        gradientDrawable.setBounds(0, 0, width, height)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        gradientDrawable.startAnimation()
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        gradientDrawable.draw(canvas!!)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        gradientDrawable.stopAnimation()
    }

    override fun verifyDrawable(who: Drawable): Boolean {
        return super.verifyDrawable(who) || who == gradientDrawable
    }
}