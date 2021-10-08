package com.applsh.androiduipractice.daangn

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.applsh.androiduipractice.R
import com.applsh.androiduipractice.base.GlideApp

class DaangnFirstImageViewHolder(
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {
    private val context = itemView.context.applicationContext

    val imageView = itemView.findViewById<ImageView>(R.id.daangn_detail_image_view)
    val progressBar: ProgressBar = itemView.findViewById(R.id.daangn_detail_image_progress_bar)

    fun bindTo(thumbnail: String, image: String) {
        val thumbnailRequest = GlideApp.with(context)
            .load(thumbnail)
            .centerCrop()
        GlideApp.with(itemView.context)
            .load(image)
            .thumbnail(thumbnailRequest)
            .centerCrop()
            .into(imageView)

        /*
        scope.launch {
            val req = ImageRequest.Builder(itemView.context)
                .data(thumbnail)
                .build()
            imageView.load(image) {
                allowHardware(false)
                listener (
                    onSuccess = { request, metadata ->
                        progress.visibility = View.GONE
                    }
                )
                placeholder(itemView.context.imageLoader.execute(req).drawable)
            }
        }
         */
    }
}