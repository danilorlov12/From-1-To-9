package com.orlovdanylo.fromonetoninegame.domain.model

import java.util.Locale

data class StatisticsModel(
    val gamesPlayed: Int,
    val gamesFinished: Int,
    val bestTime: String,
    val minPairs: Int,
    val maxPairs: Int
) {
    val winRate: String
        get() {
            val percentage = gamesFinished.toDouble() / gamesPlayed.toDouble() * 100
            return "${String.format(Locale.ENGLISH, "%.2f", percentage)} %"
        }
}