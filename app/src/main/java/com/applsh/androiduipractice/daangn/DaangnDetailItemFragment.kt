package com.applsh.androiduipractice.daangn

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Fade
import androidx.transition.doOnEnd
import androidx.transition.doOnStart
import com.applsh.androiduipractice.R
import com.applsh.androiduipractice.base.BaseFragment
import com.applsh.androiduipractice.base.StartViewHoldEndTransition
import com.applsh.androiduipractice.base.StartViewHoldStartTransition
import com.applsh.androiduipractice.base.withAlpha
import com.applsh.androiduipractice.databinding.DaangnDetailItemFragmentBinding
import com.google.android.material.animation.ArgbEvaluatorCompat
import kotlin.math.min
import kotlin.random.Random

class DaangnDetailItemFragment : BaseFragment<DaangnDetailItemFragmentBinding>() {
    override val layout: Int
        get() = R.layout.daangn_detail_item_fragment

    lateinit var item: DaangnItemModel

    lateinit var fakeStatusBar: View
    lateinit var toolbar: Toolbar
    lateinit var navigationIcon: Drawable

    private val navigationIconArgbEvaluator = ArgbEvaluatorCompat.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        enterTransition = Fade(Fade.IN).apply {
            duration = 200L
        }
        returnTransition = Fade(Fade.OUT).apply {
            duration = 200L
        }

        postponeEnterTransition()

        val startTransition = StartViewHoldStartTransition().apply {
            drawingViewId = R.id.daangn_frame_layout
        }
        startTransition.duration = 400
        startTransition.doOnStart {
            binding.daangnDetailItemBlockView.visibility = View.VISIBLE
        }
        startTransition.doOnEnd {
            binding.daangnDetailItemBlockView.visibility = View.GONE
        }
        sharedElementEnterTransition = startTransition

        val endTransition = StartViewHoldEndTransition().apply {
            drawingViewId = R.id.daangn_frame_layout
        }
        endTransition.duration = 400
        sharedElementReturnTransition = endTransition

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dimenId = resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = resources.getDimensionPixelSize(dimenId)
        fakeStatusBar = binding.daangnDetailItemFakeStatusBackground

        toolbar = binding.daangnDetailItemToolBar
        (binding.daangnDetailItemFakeStatusBackground.layoutParams as ConstraintLayout.LayoutParams).height =
            statusBarHeight
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.daangnDetailItemToolBar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        navigationIcon = DrawableCompat.wrap(toolbar.navigationIcon!!).mutate()
        DrawableCompat.setTint(navigationIcon, 0xffffffff.toInt())
        toolbar.navigationIcon = navigationIcon

        updateToolbarTitle(null)
        updateAppBarAlpha(0f)

        item = requireArguments().getParcelable("item")!!
        val r = Random(System.currentTimeMillis())
        val numOfImages = r.nextInt(20, 25)
        val l = ArrayList<String>(numOfImages + 1)
        l += item.image.replace("m/300/", "m/1800/")
        for (i in 0 until numOfImages) {
            l += "https://via.placeholder.com/3600/${r.nextInt(0, 0xfff)}/${r.nextInt(0, 0xfff)}.png"
        }

        val detailItemAdapter = DaangnDetailItemAdapter(
            DaangnDetailItemModel(
                item.title,
                item.image,
                l,
                "100,000"
            )
        )

        val detailAdditionalItemAdapter = DaangnDetailAdditionalItemAdapter()

        val concatAdapter = ConcatAdapter(detailItemAdapter, detailAdditionalItemAdapter)

        with(binding.daangnDetailItemRecyclerView) {
            adapter = concatAdapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = null
            animation = null
        }

        binding.root.doOnPreDraw {
            startPostponedEnterTransition()
            val imagesViewHolder =
                binding.daangnDetailItemRecyclerView.findViewHolderForAdapterPosition(0)!!
            val toolbarGroupHeight =
                toolbar.height + binding.daangnDetailItemFakeStatusBackground.height
            val toolbarVisibleHeight = (imagesViewHolder.itemView.height - toolbarGroupHeight)
            val titleViewHolder =
                binding.daangnDetailItemRecyclerView.findViewHolderForAdapterPosition(2)!!
            val titleVisibleHeight =
                (titleViewHolder.itemView.y + titleViewHolder.itemView.height) - toolbarGroupHeight
            binding.daangnDetailItemRecyclerView.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                var currentY = 0
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    currentY += dy
                    updateAppBarAlpha(min(1f, currentY.toFloat() / toolbarVisibleHeight))
                    if (currentY < titleVisibleHeight) {
                        updateToolbarTitle(null)
                    } else {
                        updateToolbarTitle(item.title)
                    }
                }
            })
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            fakeStatusBar.visibility = View.GONE
            toolbar.visibility = View.GONE
            val imagesViewHolder =
                binding.daangnDetailItemRecyclerView.findViewHolderForAdapterPosition(0) as? DaangnDetailItemViewHolder
            imagesViewHolder?.updateTransitionName()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as AppCompatActivity).setSupportActionBar(null) // transition이 늦게 작동함
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun updateAppBarAlpha(alpha: Float) {
        val color = navigationIconArgbEvaluator.evaluate(alpha, 0xffffffff.toInt(), 0xff000000.toInt())
        DrawableCompat.setTint(navigationIcon, color)
        val background = fakeStatusBar.background
        if (alpha == 1f && (background is ColorDrawable && background.alpha != 255)) {
            fakeStatusBar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.status_bar))
            toolbar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.tool_bar))
        } else if (alpha < 1f) {
            fakeStatusBar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.status_bar).withAlpha(alpha))
            toolbar.setBackgroundColor((ContextCompat.getColor(requireContext(), R.color.tool_bar).withAlpha(alpha)))
        }
    }

    fun updateToolbarTitle(title: String?) {
        if (toolbar.title != title) {
            toolbar.title = title
        }
    }

    companion object {
        const val TAG = "DAANGN_DETAIL_ITEM_FRAGEMTN"
        fun newInstance(item: DaangnItemModel): DaangnDetailItemFragment {
            val f = DaangnDetailItemFragment()
            val b = Bundle()
            b.putParcelable("item", item)
            f.arguments = b
            return f
        }
    }
}