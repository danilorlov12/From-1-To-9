package com.orlovdanylo.fromonetoninegame.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.orlovdanylo.fromonetoninegame.R
import com.orlovdanylo.fromonetoninegame.AnalyticsTracker

class MainActivity : AppCompatActivity() {

    val analytics: AnalyticsTracker by lazy { AnalyticsTracker(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}