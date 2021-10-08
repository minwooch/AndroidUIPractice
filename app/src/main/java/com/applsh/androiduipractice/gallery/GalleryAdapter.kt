package com.applsh.androiduipractice.gallery

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.applsh.androiduipractice.R

class GalleryAdapter : ListAdapter<Uri, GalleryViewHolder>(DIFF) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.gallery_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Uri>() {
            override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
                return oldItem.toString() == newItem.toString()
            }

            override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
                return oldItem.toString() == newItem.toString()
            }
        }
    }
}