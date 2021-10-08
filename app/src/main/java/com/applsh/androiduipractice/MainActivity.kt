package com.applsh.androiduipractice

import android.content.Intent
import android.os.Bundle
import com.applsh.androiduipractice.base.BaseActivity
import com.applsh.androiduipractice.daangn.DaangnActivity
import com.applsh.androiduipractice.databinding.ActivityMainBinding
import com.applsh.androiduipractice.gallery.GalleryActivity
import com.applsh.androiduipractice.naver.NaverActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layout: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.naverButton.setOnClickListener {
            startActivity(
                Intent(
                    this, NaverActivity::class.java
                )
            )
        }

        binding.daangnButton.setOnClickListener {
            startActivity(
                Intent(
                    this, DaangnActivity::class.java
                )
            )
        }

        binding.galleryButton.setOnClickListener {
            startActivity(
                Intent(
                    this, GalleryActivity::class.java
                )
            )
        }
    }
}