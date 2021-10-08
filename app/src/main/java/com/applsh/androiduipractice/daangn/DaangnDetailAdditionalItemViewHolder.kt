package com.applsh.androiduipractice.daangn

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.applsh.androiduipractice.R

class DaangnDetailAdditionalItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val titleTextView = itemView.findViewById<TextView>(R.id.daangn_detail_title_text_view)

    fun bindTo(s: String) {
        titleTextView.text = s
    }
}