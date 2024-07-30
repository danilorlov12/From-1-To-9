package com.orlovdanylo.fromonetoninegame.data.mappers

import com.orlovdanylo.fromonetoninegame.GameMode
import com.orlovdanylo.fromonetoninegame.data.statistics.StatisticsModelEntity
import com.orlovdanylo.fromonetoninegame.domain.model.StatisticsModel
import com.orlovdanylo.fromonetoninegame.domain.model.TimeModel

class StatisticsMapper {

    fun toStatisticsModel(statistics: List<StatisticsModelEntity>?): List<StatisticsModel> {
        return statistics?.map {
            StatisticsModel(
                mode = GameMode.gameModeById(it.id),
                gamesPlayed = it.gamesPlayed ?: 0,
                gamesFinished = it.gamesFinished ?: 0,
                bestTime = run {
                    val timeModel = TimeModel(it.bestTime?.toLongOrNull() ?: 0L)
                    timeModel.displayableTime()
                },
                minPairs = it.minPairs ?: 0,
                maxPairs = it.maxPairs ?: 0
            )
        } ?: emptyList()
    }
}