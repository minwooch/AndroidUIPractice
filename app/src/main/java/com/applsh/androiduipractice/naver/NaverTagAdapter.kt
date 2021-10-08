package com.applsh.androiduipractice.naver

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.applsh.androiduipractice.R

class NaverTagAdapter : RecyclerView.Adapter<NaverTagViewHolder>() {
    private val itemList = arrayListOf(
        NaverTagModel(
            title = "선별 진료소",
            image = ""
        ),
        NaverTagModel(
            title = "음식점",
            image = ""
        ),
        NaverTagModel(
            title = "카페",
            image = ""
        ),
        NaverTagModel(
            title = "픽업주문",
            image = ""
        ),
        NaverTagModel(
            title = "편의점",
            image = ""
        ),
        NaverTagModel(
            title = "주유소",
            image = ""
        ),
        NaverTagModel(
            title = "마트",
            image = ""
        ),
        NaverTagModel(
            title = "은행",
            image = ""
        ),
        NaverTagModel(
            title = "헤어샵",
            image = ""
        ),
        NaverTagModel(
            title = "주차장",
            image = ""
        ),
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NaverTagViewHolder {
        return NaverTagViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.naver_tag_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NaverTagViewHolder, position: Int) {
        holder.bindTo(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun simpleNotify() {
        notifyItemRangeInserted(0, itemList.size)
    }
}