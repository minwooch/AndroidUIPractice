package com.applsh.androiduipractice.base

import android.graphics.*
import android.os.Build
import android.view.View
import android.widget.ImageView
import kotlin.math.min


fun View.toImageView(): ImageView {
    val copy = ImageView(context)
    copy.scaleType = ImageView.ScaleType.CENTER_CROP
    val bitmap = toBitmap()
    copy.setImageBitmap(bitmap)

    val widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
    val heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
    copy.measure(widthSpec, heightSpec)
    copy.layout(left, top, right, bottom)
    return copy
}


fun View.toBitmap(): Bitmap {
    var bitmapWidth = width
    var bitmapHeight = height
    val scale = min(1f, 1024 * 1024.toFloat() / (bitmapWidth * bitmapHeight))
    bitmapWidth = Math.round(bitmapWidth * scale)
    bitmapHeight = Math.round(bitmapHeight * scale)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        // Hardware rendering
        val picture = Picture()
        val canvas = picture.beginRecording(bitmapWidth, bitmapHeight)
        canvas.concat(matrix)
        draw(canvas)
        picture.endRecording()
        return Bitmap.createBitmap(picture)
    } else {
        // Software rendering
        val bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.concat(matrix)
        draw(canvas)
        return bitmap
    }
}