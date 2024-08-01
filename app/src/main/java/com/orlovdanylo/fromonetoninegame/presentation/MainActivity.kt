package com.orlovdanylo.fromonetoninegame.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import com.orlovdanylo.fromonetoninegame.AnalyticsTracker
import com.orlovdanylo.fromonetoninegame.MainViewModel
import com.orlovdanylo.fromonetoninegame.R

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    val analytics: AnalyticsTracker by lazy { AnalyticsTracker(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController

        val inflater = navHostFragment.navController.navInflater

        navController.graph = inflater.inflate(R.navigation.main_navigation).apply {
            setStartDestination(obtainStartDestination())
        }
    }

    private fun obtainStartDestination() = when (viewModel.isFirstLaunch()) {
        true -> R.id.howToPlayFragment
        false -> R.id.menuFragment
    }
}