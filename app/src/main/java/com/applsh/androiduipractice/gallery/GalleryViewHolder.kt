package com.applsh.androiduipractice.gallery

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.applsh.androiduipractice.R
import com.applsh.androiduipractice.base.GlideApp

class GalleryViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    val imageView = itemView.findViewById<ImageView>(R.id.gallery_item_image_view)

    fun bindTo(uri: Uri) {
        GlideApp.with(itemView.context)
            .load(uri)
            .into(imageView)
    }
}