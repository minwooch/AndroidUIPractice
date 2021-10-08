package com.applsh.androiduipractice.naver

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.applsh.androiduipractice.R

class NaverTagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindTo(item: NaverTagModel) {
        itemView.findViewById<AppCompatTextView>(R.id.naver_tag_item_text_view).text = item.title
    }
}