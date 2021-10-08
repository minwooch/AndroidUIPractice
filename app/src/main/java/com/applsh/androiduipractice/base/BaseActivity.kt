package com.applsh.androiduipractice.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.applsh.androiduipractice.daangn.DaangnMainFragment

abstract class BaseActivity<T: ViewDataBinding> : AppCompatActivity() {

    lateinit var binding : T

    abstract val layout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layout)
    }
}