package com.applsh.androiduipractice.daangn

import android.view.View
import android.widget.ImageView
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.applsh.androiduipractice.R

class DaangnDetailItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageAdapter = DaangnImageAdapter()
    private val vp = itemView.findViewById<ViewPager2>(R.id.daangn_detail_item_item_images_view_pager)

    init {
        vp.adapter = imageAdapter
    }

    fun bindTo(item: DaangnDetailItemModel) {
        imageAdapter.thumbnail = item.thumbnail
        imageAdapter.setImages(item.images)
    }

    fun updateTransitionName() {
        val position = vp.currentItem
        val vh = (vp[0] as RecyclerView).findViewHolderForAdapterPosition(position)!!
        if (vh is DaangnFirstImageViewHolder) {
            vh.progressBar.visibility = View.GONE
            if (vh.imageView.drawable == null) {
                vp.transitionName = null
            } else {
                vp.transitionName = vp.context.getString(R.string.daangn_item_image_transition)
            }
        } else if (vh is DaangnImageViewHolder) {
            vh.progressBar.visibility = View.GONE
            if (vh.imageView.drawable == null) {
                vp.transitionName = null
            } else {
                vp.transitionName = vp.context.getString(R.string.daangn_item_image_transition)
            }
        } else {
            vp.transitionName = null
        }
    }
}