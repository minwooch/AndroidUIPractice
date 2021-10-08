package com.applsh.androiduipractice.daangn

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.applsh.androiduipractice.R
import com.applsh.androiduipractice.base.BaseFragment
import com.applsh.androiduipractice.databinding.DaangnMainFragmentBinding
import com.bumptech.glide.Registry

class DaangnMainFragment : BaseFragment<DaangnMainFragmentBinding>() {
    override val layout: Int
        get() = R.layout.daangn_main_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = Fade(Fade.OUT).apply {
            duration = 200L
        }
        reenterTransition = Fade(Fade.IN).apply {
            duration = 200L
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dimenId = resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = resources.getDimensionPixelSize(dimenId)
        (binding.daangnMainFakeStatusBackground.layoutParams as ConstraintLayout.LayoutParams).height = statusBarHeight

        postponeEnterTransition()
        binding.daangnViewPager.doOnPreDraw {
            startPostponedEnterTransition()
        }

        val pagerAdapter = DaangnMainPagerAdapter()

        binding.daangnViewPager.adapter = pagerAdapter
    }


    private inner class DaangnMainPagerAdapter() : FragmentStateAdapter(childFragmentManager, viewLifecycleOwner.lifecycle) {
        override fun getItemCount(): Int = 1

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> {
                    DaangnMainFirstFragment.newInstance()
                }
                1 -> {
                    DaangnMainFirstFragment.newInstance()
                }
                else -> throw Exception("")
            }
        }
    }

    companion object {
        const val TAG = "DAANGN_MAIN_FRAGMENT"
        fun newInstance(): DaangnMainFragment {
            return DaangnMainFragment()
        }
    }
}