package com.applsh.androiduipractice.daangn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.applsh.androiduipractice.R

class DaangnItemAdapter(private val onClickItemListener: OnClickItemListener) :
    RecyclerView.Adapter<DaangnItemViewHolder>() {

    interface OnClickItemListener {
        fun onClickItem(view: View, item: DaangnItemModel?)
    }

    private val itemList = arrayListOf(
        DaangnItemModel(
            title = "선별 진료소",
            image = "https://via.placeholder.com/300/09f/fff.png"
        ),
        DaangnItemModel(
            title = "음식점",
            image = "https://via.placeholder.com/300/08f/fff.png"
        ),
        DaangnItemModel(
            title = "카페",
            image = "https://via.placeholder.com/300/09f/eee.png"
        ),
        DaangnItemModel(
            title = "픽업주문",
            image = "https://via.placeholder.com/300/361/bae.png"
        ),
        DaangnItemModel(
            title = "편의점",
            image = "https://via.placeholder.com/300/231/dca.png"
        ),
        DaangnItemModel(
            title = "주유소",
            image = "https://via.placeholder.com/300/571/afd.png"
        ),
        DaangnItemModel(
            title = "마트",
            image = "https://via.placeholder.com/300/127/fce.png"
        ),
        DaangnItemModel(
            title = "은행",
            image = "https://via.placeholder.com/300/535/egc.png"
        ),
        DaangnItemModel(
            title = "헤어샵",
            image = "https://via.placeholder.com/300/158/fec.png"
        ),
        DaangnItemModel(
            title = "주차장",
            image = "https://via.placeholder.com/300/552/eab.png"
        ),
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaangnItemViewHolder {
        return DaangnItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.daangn_item_item, parent, false),
            onClickItemListener
        )
    }

    override fun onBindViewHolder(holder: DaangnItemViewHolder, position: Int) {
        holder.bindTo(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun simpleNotify() {
        notifyItemRangeInserted(0, itemList.size)
    }
}