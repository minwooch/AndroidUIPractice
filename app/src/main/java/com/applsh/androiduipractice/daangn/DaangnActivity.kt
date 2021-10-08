package com.applsh.androiduipractice.daangn

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.applsh.androiduipractice.R
import com.applsh.androiduipractice.base.BaseActivity
import com.applsh.androiduipractice.databinding.DaangnActivityBinding

class DaangnActivity : BaseActivity<DaangnActivityBinding>() {

    override val layout: Int
        get() = R.layout.daangn_activity

    private val mainFragment = DaangnMainFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(binding.daangnFrameLayout.id, mainFragment, DaangnMainFragment.TAG)
                .commitNow()
        }
    }

    fun mainToDetail(view: View, item: DaangnItemModel) {
        supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .addSharedElement(view, getString(R.string.daangn_item_image_transition))
            .add(
                binding.daangnFrameLayout.id,
                DaangnDetailItemFragment.newInstance(item),
                DaangnDetailItemFragment.TAG
            )
            .detach(mainFragment)
            .addToBackStack(null)
            .commit()
    }
}