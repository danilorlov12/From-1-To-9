package com.orlovdanylo.fromonetoninegame.data.game

import com.orlovdanylo.fromonetoninegame.domain.GameRepository

class GameRepositoryImpl(
    private val gameDao: GameDao
) : GameRepository {

    override suspend fun saveGameToDatabase(gameDbModel: GameModelDB) {
        gameDao.insert(gameDbModel)
    }

    override suspend fun isGameSavedInDatabase(): Boolean {
        return gameDao.findUnfinishedGame() != null
    }

    override suspend fun getLastGameFromDatabase(): GameModelDB? {
        return gameDao.findUnfinishedGame()
    }

    override suspend fun deleteLastGameFromDatabase() {
        gameDao.deleteUnfinishedGame()
    }
}
