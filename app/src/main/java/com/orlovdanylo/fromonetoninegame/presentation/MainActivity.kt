package com.orlovdanylo.fromonetoninegame.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.orlovdanylo.fromonetoninegame.R
import com.orlovdanylo.fromonetoninegame.AnalyticsTracker

class MainActivity : AppCompatActivity() {

    val analytics: AnalyticsTracker by lazy { AnalyticsTracker(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}