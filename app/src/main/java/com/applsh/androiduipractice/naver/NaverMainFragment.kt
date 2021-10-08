package com.applsh.androiduipractice.naver

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.databinding.DataBindingUtil
import androidx.transition.TransitionInflater
import com.applsh.androiduipractice.R
import com.applsh.androiduipractice.base.BaseFragment
import com.applsh.androiduipractice.databinding.NaverBottomSheetScrollViewBinding
import com.applsh.androiduipractice.databinding.NaverMainFragmentBinding

class NaverMainFragment : BaseFragment<NaverMainFragmentBinding>() {
    override val layout: Int
        get() = R.layout.naver_main_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvSearch.setOnClickListener {
            (requireActivity() as NaverActivity).mainToSearch(binding.naverMainSearchLayout)
        }
        exitTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.fade)
            .apply {
                duration = 200L
            }

        binding.naverMainScrollOpenButton.setOnClickListener {
            when (binding.naverMainMotionLayout.currentState) {
                R.id.naver_main_scroll_hidden -> {
                    binding.naverMainMotionLayout.transitionToState(R.id.naver_main_scroll_half)
                }
            }
        }

        binding.naverMainScrollLayout.setScrollingEnabled(false)

        binding.naverMainMotionLayout.addTransitionListener(
            object : TransitionAdapter() {

                override fun onTransitionStarted(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int
                ) {
                    binding.naverMainScrollLayout.setScrollingEnabled(true)
                }

                override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                    if (currentId == R.id.naver_main_scroll_half) {
                        binding.naverMainScrollLayout.scrollTo(0, 0)
                        binding.naverMainScrollLayout.setScrollingEnabled(false)
                    }
                }

                override fun onTransitionChange(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {
                    val startIdString: String
                    val endIdString: String
                    if (startId == R.id.naver_main_scroll_hidden) {
                        startIdString = "hidden"
                    } else if (startId == R.id.naver_main_scroll_half) {
                        startIdString = "half"
                    } else {
                        startIdString = "full"
                    }

                    if (endId == R.id.naver_main_scroll_hidden) {
                        endIdString = "hidden"
                    } else if (endId == R.id.naver_main_scroll_half) {
                        endIdString = "half"
                    } else {
                        endIdString = "full"
                    }
                    Log.d("HELLO", "${startIdString} ${endIdString} ${progress}")
                }
            }
        )
    }

    companion object {
        const val TAG = "NAVER_MAIN_FRAGMENT"
        fun newInstance() = NaverMainFragment()
    }
}