package com.applsh.androiduipractice.daangn

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Fade
import com.applsh.androiduipractice.R
import com.applsh.androiduipractice.base.BaseFragment
import com.applsh.androiduipractice.databinding.DaangnMainFirstFragmentBinding

class DaangnMainFirstFragment : BaseFragment<DaangnMainFirstFragmentBinding>(),
    DaangnItemAdapter.OnClickItemListener {
    override val layout: Int
        get() = R.layout.daangn_main_first_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemAdapter = DaangnItemAdapter(this)
        with(binding.daangnMainFirstItemRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = itemAdapter
            animation = null
            itemAnimator = null
        }
    }

    override fun onClickItem(view: View, item: DaangnItemModel?) {
        if (item != null)
            (requireActivity() as DaangnActivity).mainToDetail(view, item)
    }

    companion object {
        const val TAG = "DAANGN_MAIN_FIRST_FRAGMENT"
        fun newInstance(): DaangnMainFirstFragment {
            return DaangnMainFirstFragment()
        }
    }
}