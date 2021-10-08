package com.applsh.androiduipractice.daangn

import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.applsh.androiduipractice.R
import com.applsh.androiduipractice.base.GlideApp

class DaangnItemViewHolder(
    itemView: View,
    private val onClickItemListener: DaangnItemAdapter.OnClickItemListener
) : RecyclerView.ViewHolder(itemView) {

    var item: DaangnItemModel? = null
    private val textView =
        itemView.findViewById<AppCompatTextView>(R.id.daangn_item_title_text_view)
    private val materialCardView = itemView.findViewById<View>(R.id.daangn_item_image_view)
    private val imageView = itemView.findViewById<ImageView>(R.id.daangn_item_image_internal_view)

    private val USER_AGENT =
        "Mozilla/5.0 (Linux; Android 11) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.181 Mobile Safari/537.36"


    init {
        itemView.findViewById<View>(R.id.daangn_item_constraint_layout).setOnClickListener {
            onClickItemListener.onClickItem(
                materialCardView,
                item
            )
        }
    }

    fun bindTo(item: DaangnItemModel) {
        this.item = item
        textView.text = item.title
        materialCardView.transitionName = item.image
        GlideApp.with(imageView)
            .load(item.image)
            .into(imageView)
        /*
        imageView.load(item.image) {
            placeholderMemoryCacheKey(UUID.randomUUID().toString())
            allowHardware(false)
        }
         */

    }
}