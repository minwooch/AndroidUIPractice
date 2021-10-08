package com.applsh.androiduipractice.daangn

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.applsh.androiduipractice.R
import com.applsh.androiduipractice.base.GlideApp

class DaangnImageViewHolder(
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    val imageView = itemView.findViewById<ImageView>(R.id.daangn_detail_image_view)
    val context = itemView.context.applicationContext
    val progressBar: ProgressBar = itemView.findViewById(R.id.daangn_detail_image_progress_bar)

    fun bindTo(image: String) {
        GlideApp.with(context)
            .load(image)
            .centerCrop()
            .into(imageView)
    }
}