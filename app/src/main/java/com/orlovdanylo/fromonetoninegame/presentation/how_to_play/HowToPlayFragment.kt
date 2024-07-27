package com.orlovdanylo.fromonetoninegame.presentation.how_to_play

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.orlovdanylo.fromonetoninegame.ButtonActions
import com.orlovdanylo.fromonetoninegame.R
import com.orlovdanylo.fromonetoninegame.presentation.core.BaseFragment
import com.orlovdanylo.fromonetoninegame.presentation.how_to_play.rule_content.RuleContentPagerAdapter
import com.orlovdanylo.fromonetoninegame.utils.logEventClickListener

class HowToPlayFragment : BaseFragment<HowToPlayViewModel>() {

    override val layoutId: Int = R.layout.fragment_how_to_play
    override val viewModel: HowToPlayViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager).apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    viewModel.selectedPagePosition = position
                }
            })
            adapter = RuleContentPagerAdapter(requireActivity())
        }

        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()

        view.findViewById<TextView>(R.id.btnBack).logEventClickListener(requireContext(), ButtonActions.PREVIOUS_PAGE) {
            navigateToTargetPage(viewPager, viewModel.selectedPagePosition - 1)
        }

        view.findViewById<TextView>(R.id.btnNext).logEventClickListener(requireContext(), ButtonActions.NEXT_PAGE) {
            navigateToTargetPage(viewPager, viewModel.selectedPagePosition + 1)
        }
    }

    private fun navigateToTargetPage(viewPager: ViewPager2, position: Int) {
        if (position in 0..4) {
            viewPager.currentItem = position
        } else {
            navController.navigateUp()
        }
    }

    override fun clear() = Unit
}