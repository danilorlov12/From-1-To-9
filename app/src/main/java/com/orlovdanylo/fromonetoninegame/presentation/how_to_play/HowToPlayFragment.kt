package com.orlovdanylo.fromonetoninegame.presentation.how_to_play

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
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
    override val viewModel: HowToPlayViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager).apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    viewModel.selectedPagePosition.value = position
                }
            })
            adapter = RuleContentPagerAdapter(requireActivity())
        }

        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()

        val navigationManager = ViewPagerNavigationManager(viewPager, navController) {
            viewModel.updateFirstLaunch()
        }

        view.findViewById<TextView>(R.id.btnBack).logEventClickListener(requireContext(), ButtonActions.PREVIOUS_PAGE) {
            viewModel.selectedPagePosition.value?.let {
                navigationManager.handleNavigation(it - 1)
            }
        }

        view.findViewById<TextView>(R.id.btnNext).logEventClickListener(requireContext(), ButtonActions.NEXT_PAGE) {
            viewModel.selectedPagePosition.value?.let {
                navigationManager.handleNavigation(it + 1)
            }
        }

        viewModel.selectedPagePosition.observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.btnBack).visibility =
                if (viewModel.hideBackButtonOnFirstPage()) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
        }
    }

    override fun clear() = Unit
}