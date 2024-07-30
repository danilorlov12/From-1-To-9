package com.orlovdanylo.fromonetoninegame.domain

import com.orlovdanylo.fromonetoninegame.GameMode
import com.orlovdanylo.fromonetoninegame.domain.model.StatisticsModel

interface StatisticsRepository {
    suspend fun getStatistics(): List<StatisticsModel>
    suspend fun increasePlayedGame(mode: GameMode)
    suspend fun updateFinishedGameStatistics(time: Long, pairs: Int, mode: GameMode)
}