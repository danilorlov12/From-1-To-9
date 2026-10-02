package com.orlovdanylo.fromonetoninegame

import com.orlovdanylo.fromonetoninegame.data.game.GameModelDB
import com.orlovdanylo.fromonetoninegame.domain.GameRepository
import com.orlovdanylo.fromonetoninegame.domain.StatisticsRepository
import com.orlovdanylo.fromonetoninegame.domain.model.StatisticsModel

class FakeGameRepository : GameRepository {
    private var lastGame: GameModelDB? = null

    override suspend fun saveGameToDatabase(gameDbModel: GameModelDB) {
        lastGame = gameDbModel
    }

    override suspend fun isGameSavedInDatabase(): Boolean = lastGame != null

    override suspend fun getLastGameFromDatabase(): GameModelDB? = lastGame

    override suspend fun deleteLastGameFromDatabase() {
        lastGame = null
    }

    override fun obtainGameModelsByMode(mode: GameMode): MutableList<GameModel> =
        GameModelsProvider(mode).obtainGameModels()
}

class FakeStatisticsRepository : StatisticsRepository {
    override suspend fun getStatistics(): List<StatisticsModel> = emptyList()

    override suspend fun increasePlayedGame(mode: GameMode) = Unit

    override suspend fun updateFinishedGameStatistics(time: Long, pairs: Int, mode: GameMode) = Unit
}
