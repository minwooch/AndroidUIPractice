package com.applsh.androiduipractice.daangn

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.applsh.androiduipractice.R

class DaangnImageAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val imageList = arrayListOf<String>()

    lateinit var thumbnail: String

    fun setImages(images: List<String>) {
        imageList += images
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return 1
        else return 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            1 -> {
                return DaangnFirstImageViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.daangn_detail_image_item, parent, false)
                )
            }
            2 -> {
                return DaangnImageViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.daangn_detail_image_item, parent, false),
                )
            }
            else -> throw Exception("")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0) {
            (holder as DaangnFirstImageViewHolder).bindTo(thumbnail, imageList[position])
        } else {
            (holder as DaangnImageViewHolder).bindTo(imageList[position])
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}