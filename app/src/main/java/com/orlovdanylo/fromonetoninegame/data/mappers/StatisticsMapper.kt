package com.orlovdanylo.fromonetoninegame.data.mappers

import com.orlovdanylo.fromonetoninegame.GameMode
import com.orlovdanylo.fromonetoninegame.data.statistics.StatisticsModelEntity
import com.orlovdanylo.fromonetoninegame.domain.model.StatisticsModel
import com.orlovdanylo.fromonetoninegame.domain.model.TimeModel

class StatisticsMapper {

    fun toStatisticsModel(statistics: List<StatisticsModelEntity>?): List<StatisticsModel> {
        return GameMode.playableGameModes().map { mode ->
            val stat = statistics?.find { it.id == mode.id }
            StatisticsModel(
                mode = mode,
                gamesPlayed = stat?.gamesPlayed ?: 0,
                gamesFinished = stat?.gamesFinished ?: 0,
                bestTime = run {
                    val timeModel = TimeModel(stat?.bestTime?.toLongOrNull() ?: 0L)
                    timeModel.displayableTime()
                },
                minPairs = stat?.minPairs ?: 0,
                maxPairs = stat?.maxPairs ?: 0
            )
        }
    }
}