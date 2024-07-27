package com.orlovdanylo.fromonetoninegame.presentation.how_to_play.rule_content

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class RuleContentPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int) = RuleContentFragment.newInstance(position)
}