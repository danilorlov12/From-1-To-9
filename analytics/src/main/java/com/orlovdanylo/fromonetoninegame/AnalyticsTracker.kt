package com.orlovdanylo.fromonetoninegame

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsTracker(context: Context) {

    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    fun logScreenEvent(screenName: String) {
        val eventParams = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenName)
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, eventParams)
    }

    fun logButtonEvent(buttonName: String) {
        val eventParams = Bundle().apply {
            putString(FirebaseAnalytics.Param.ITEM_NAME, buttonName)
        }
        firebaseAnalytics.logEvent(BUTTON_PRESSED, eventParams)
    }

    companion object {
        private const val BUTTON_PRESSED = "button_pressed"
    }
}