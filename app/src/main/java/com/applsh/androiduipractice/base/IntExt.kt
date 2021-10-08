package com.applsh.androiduipractice.base

fun Int.withAlpha(alpha: Float): Int {
    val a = if (alpha < 0) 0f else if (alpha > 1f) 1f else alpha
    return (255 * a).toInt().shl(24) + this.and(0xFFFFFF)
}