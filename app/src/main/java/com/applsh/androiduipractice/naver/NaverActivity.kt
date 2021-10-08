package com.applsh.androiduipractice.naver

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.applsh.androiduipractice.R
import com.applsh.androiduipractice.base.BaseActivity
import com.applsh.androiduipractice.databinding.NaverActivityBinding

class NaverActivity : BaseActivity<NaverActivityBinding>() {

    override val layout: Int
        get() = R.layout.naver_activity

    val naverMainFragment = NaverMain2Fragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(binding.naverFrameLayout.id, naverMainFragment, NaverMain2Fragment.TAG)
                .commitNow()
        }

        onBackPressedDispatcher.addCallback(this) {
            if (binding.naverActivityDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                closeDrawer()
            } else {
                this.isEnabled = false
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    fun mainToSearch(view: View) {
        supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .addSharedElement(view, getString(R.string.naver_search_transition))
            .add(
                binding.naverFrameLayout.id,
                NaverSearchFragment.newInstance(),
                NaverSearchFragment.TAG
            )
            .detach(naverMainFragment)
//            .replace(binding.naverFrameLayout.id, NaverSearchFragment.newInstance(), NaverSearchFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    fun openDrawer() {
        binding.naverActivityDrawerLayout.openDrawer(GravityCompat.START)
    }

    fun closeDrawer() {
        binding.naverActivityDrawerLayout.closeDrawer(GravityCompat.END)
    }
}