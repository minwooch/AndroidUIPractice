package com.applsh.androiduipractice.naver

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.view.doOnLayout
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.applsh.androiduipractice.R
import com.applsh.androiduipractice.base.BaseFragment
import com.applsh.androiduipractice.databinding.NaverMain2FragmentBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class NaverMain2Fragment : BaseFragment<NaverMain2FragmentBinding>() {
    override val layout: Int
        get() = R.layout.naver_main_2_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.naverMainSearchTextView.setOnClickListener {
            (requireActivity() as NaverActivity).mainToSearch(binding.naverMainSearchLayout)
        }
        exitTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.fade)
            .apply {
                duration = 150L
            }

        binding.naverMainOpenDrawer.setOnClickListener {
            (requireActivity() as NaverActivity).openDrawer()
        }

        val tagAdapter = NaverTagAdapter()
        with(binding.naverMainTagRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = tagAdapter
            animation = null
            itemAnimator = null
            addItemDecoration(
                object : RecyclerView.ItemDecoration() {

                    val itemMargin =
                        context.resources.getDimensionPixelSize(R.dimen.naver_recycler_view_margin)

                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        val index = parent.getChildLayoutPosition(view)
                        if (index == RecyclerView.NO_POSITION) {
                            super.getItemOffsets(outRect, view, parent, state)
                        } else {
                            if (index != state.itemCount - 1) {
                                outRect.right += itemMargin
                            }
                        }
                    }
                }
            )
        }
        tagAdapter.simpleNotify()

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.naverMainScrollLayout)

        binding.naverMainButtomSheetButton.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        var locationButtonDefaultY = 0f
        var locationButtonBottomY = 0f
        binding.naverMain2MotionLayout.doOnLayout {
            locationButtonDefaultY =
                (binding.naverMainCoordinatorLayout.height - bottomSheetBehavior.peekHeight - binding.naverMainLocationButton.height - binding.naverMainLocationButton.marginBottom)
                    .toFloat()
            locationButtonBottomY =
                (binding.naverMainCoordinatorLayout.height - binding.naverMainLocationButton.height - binding.naverMainLocationButton.marginBottom)
                    .toFloat()
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN) {
                binding.naverMainLocationButton.y = locationButtonBottomY
            } else {
                binding.naverMainLocationButton.y = locationButtonDefaultY
            }
        }


        bottomSheetBehavior.addBottomSheetCallback(
            object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    if (slideOffset > 0) {
                        if (binding.naverMainLocationButton.y != locationButtonDefaultY) {
                            binding.naverMainLocationButton.y = locationButtonDefaultY
                        }
                    } else if (slideOffset >= -1) {
                        binding.naverMainLocationButton.y =
                            bottomSheet.y - binding.naverMainLocationButton.height - binding.naverMainLocationButton.marginBottom
                    } else {
                        if (binding.naverMainLocationButton.y != locationButtonBottomY) {
                            binding.naverMainLocationButton.y = locationButtonBottomY
                        }
                    }
                }
            }
        )
    }

    companion object {
        const val TAG = "NAVER_MAIN_2_FRAGMENT"
        fun newInstance() = NaverMain2Fragment()
    }
}