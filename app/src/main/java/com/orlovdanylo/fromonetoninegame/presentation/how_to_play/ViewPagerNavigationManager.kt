package com.orlovdanylo.fromonetoninegame.presentation.how_to_play

import androidx.navigation.NavController
import androidx.viewpager2.widget.ViewPager2
import com.orlovdanylo.fromonetoninegame.R

class ViewPagerNavigationManager(
    private val viewPager: ViewPager2,
    private val navController: NavController,
    private val action: () -> Unit
) {

    fun handleNavigation(position: Int) {
        if (position in 0..4) {
            viewPager.currentItem = position
        } else {
            handleFirstLaunchNavigation()
        }
    }

    private fun handleFirstLaunchNavigation() {
        if (navController.graph.startDestinationId == R.id.howToPlayFragment) {
            action.invoke()
            navController.navigate(R.id.menuFragment)
        } else {
            navController.navigateUp()
        }
    }
}