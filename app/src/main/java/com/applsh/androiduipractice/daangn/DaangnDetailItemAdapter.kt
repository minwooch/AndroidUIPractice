package com.applsh.androiduipractice.daangn

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.applsh.androiduipractice.R

class DaangnDetailItemAdapter(private val item: DaangnDetailItemModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemList = arrayListOf<Any>()

    init {
        itemList.add(item)
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return R.layout.daangn_detail_images_item
        } else if (position == 1) {
            return R.layout.daangn_detail_user_placeholder_item
        } else if (position == 2) {
            return R.layout.daangn_detail_title_item
        }
        return -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.daangn_detail_images_item -> {
                DaangnDetailItemViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.daangn_detail_images_item, parent, false)
                )
            }
            R.layout.daangn_detail_user_placeholder_item -> {
                DaangnDetailUserPlaceholderViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.daangn_detail_user_placeholder_item, parent, false)
                )
            }
            R.layout.daangn_detail_title_item -> {
                DaangnDetailTitleViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.daangn_detail_title_item, parent, false)
                )
            }
            else -> {
                throw Exception("")
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0) {
            (holder as DaangnDetailItemViewHolder).bindTo(item)
        } else if (position == 2) {
            (holder as DaangnDetailTitleViewHolder).bindTo(item)
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    fun simpleNotify() {
        // notifyItemInserted(0)
    }
}