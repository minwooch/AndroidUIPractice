package com.applsh.androiduipractice.naver

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.doOnPreDraw
import androidx.transition.AutoTransition
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import com.applsh.androiduipractice.R
import com.applsh.androiduipractice.base.BaseFragment
import com.applsh.androiduipractice.databinding.NaverSearchFragmentBinding

class NaverSearchFragment : BaseFragment<NaverSearchFragmentBinding>() {

    override val layout: Int
        get() = R.layout.naver_search_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.fade).apply {
            duration = 150L
        }
        sharedElementEnterTransition = AutoTransition().apply {
            duration = 250L
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.naverSearchEditText, 0)

        binding.naverSearchBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    companion object {
        const val TAG = "NAVER_SEARCH_FRAGMENT"
        fun newInstance(): NaverSearchFragment = NaverSearchFragment()
    }
}