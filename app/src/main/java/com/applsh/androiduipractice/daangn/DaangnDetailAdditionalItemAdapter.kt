package com.applsh.androiduipractice.daangn

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.applsh.androiduipractice.R

class DaangnDetailAdditionalItemAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemList = arrayListOf<DaangnItemModel>()

    init {
        for (i in 0 until 30) {
            itemList.add(
                DaangnItemModel("제목 $i", "a")
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DaangnDetailAdditionalItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.daangn_detail_title_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DaangnDetailAdditionalItemViewHolder).bindTo(itemList[position].title)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun simpleNotify() {
        // notifyItemInserted(0)
    }
}