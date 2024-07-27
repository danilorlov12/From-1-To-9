package com.orlovdanylo.fromonetoninegame.domain.model

import java.util.Locale
import java.util.concurrent.TimeUnit

class TimeModel(
    private val time: Long
) {
    fun displayableTime(): String {
        val hours = TimeUnit.MILLISECONDS.toHours(time)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(time) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(time) % 60
        return String.format(Locale.ENGLISH, "%02d:%02d:%02d", hours, minutes, seconds)
    }
}