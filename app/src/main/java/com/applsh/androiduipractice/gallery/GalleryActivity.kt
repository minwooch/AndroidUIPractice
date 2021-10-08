package com.applsh.androiduipractice.gallery

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.recyclerview.widget.GridLayoutManager
import com.applsh.androiduipractice.R
import com.applsh.androiduipractice.base.BaseActivity
import com.applsh.androiduipractice.databinding.GalleryActivityBinding

class GalleryActivity : BaseActivity<GalleryActivityBinding>() {
    override val layout: Int
        get() = R.layout.gallery_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
        )
        val q = applicationContext.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            "${MediaStore.Images.Media.DATE_ADDED} DESC"
        )
        val list = ArrayList<Uri>()
        q?.use {
            while (it.moveToNext()) {
                val id = it.getLong(0)
                val contentUri = Uri.withAppendedPath(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id.toString()
                )
                list += contentUri
            }
        }

        val galleryAdapter = GalleryAdapter()
        with(binding.galleryRecyclerView) {
            adapter = galleryAdapter
            layoutManager = GridLayoutManager(this.context, 4, GridLayoutManager.VERTICAL, false)
            itemAnimator = null
            animation = null
        }
        galleryAdapter.submitList(list)
    }
}