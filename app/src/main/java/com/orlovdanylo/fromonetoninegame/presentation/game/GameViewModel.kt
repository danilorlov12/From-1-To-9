package com.orlovdanylo.fromonetoninegame.presentation.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.orlovdanylo.fromonetoninegame.Repositories
import com.orlovdanylo.fromonetoninegame.base.BaseViewModel
import com.orlovdanylo.fromonetoninegame.data.game.GameModelDB
import com.orlovdanylo.fromonetoninegame.presentation.game.models.GameModel
import com.orlovdanylo.fromonetoninegame.presentation.game.models.NumberRemoval
import com.orlovdanylo.fromonetoninegame.presentation.game.undo_redo_operations.IUndoRedoOperation
import com.orlovdanylo.fromonetoninegame.presentation.game.undo_redo_operations.UndoRedoOperation
import com.orlovdanylo.fromonetoninegame.utils.GameUtils
import kotlinx.coroutines.launch

class GameViewModel : BaseViewModel(), IUndoRedoOperation by UndoRedoOperation() {

    private val gameRepository = Repositories.gameRepository
    private val statisticsRepository = Repositories.statisticsRepository

    val removedNumbers: MutableLiveData<Int> = MutableLiveData(0)
    val gameModels: MutableLiveData<MutableList<GameModel>> = MutableLiveData()
    val gameModelsCount: MutableLiveData<Int> = MutableLiveData()
    val selectedModel: MutableLiveData<GameModel?> = MutableLiveData()
    val pairNumbers: MutableLiveData<Pair<Int, Int>> = MutableLiveData()

    val isGameFinished: MutableLiveData<Boolean> = MutableLiveData()
    val startTime: MutableLiveData<Long> = MutableLiveData()
    val gameTime: MutableLiveData<Long> = MutableLiveData()

    fun initGame(isNewGame: Boolean) {
        viewModelScope.launch {
            if (isNewGame) initNewGame() else initOldGame()
            gameModelsCount.value = gameModels.value!!.count { !it.isCrossed }
        }
    }

    private suspend fun initNewGame() {
        gameRepository.deleteLastGameFromDatabase()
        statisticsRepository.increasePlayedGame()
        gameModels.value = GameUtils.game.mapIndexed { index, s ->
            GameModel(index, s.toInt(), false)
        }.toMutableList()
        startTime.value = 0L
    }

    private suspend fun initOldGame() {
        val storedGame = gameRepository.getLastGameFromDatabase()
        removedNumbers.value = storedGame?.pairCrossed ?: 0
        gameModels.value = convertToDisplayableGame(storedGame!!)
    }

    fun initGameTime() {
        viewModelScope.launch {
            val storedGame = gameRepository.getLastGameFromDatabase()
            startTime.value = storedGame?.time ?: 0L
        }
    }

    fun tap(id: Int) {
        val gameModel = gameModels.value!!.find { it.id == id } ?: return

        if (gameModel.isCrossed) return

        if (gameModel.id == (selectedModel.value?.id ?: false)) {
            selectedModel.value = null
            return
        }

        selectedModel.value = if (selectedModel.value == null) {
            gameModel
        } else {
            checkNumbers(gameModel)
            null
        }
    }

    fun updateNumbers() {
        val lastModelId = gameModels.value!!.last().id + 1
        val restValues = gameModels.value!!.filter { !it.isCrossed }

        gameModels.value = (gameModels.value!! + restValues.mapIndexed { index, model ->
            GameModel(index + lastModelId, model.num, false)
        }).toMutableList()
        gameModelsCount.value = gameModels.value!!.count { !it.isCrossed }

        updateStacks(arrayListOf(), arrayListOf())
    }

    private fun checkNumbers(gameModel: GameModel) {
        if (GameUtils.checkNumbers(selectedModel.value!!.num, gameModel.num)) {
            val (start, end) = if (selectedModel.value!!.id < gameModel.id) {
                selectedModel.value!!.id to gameModel.id
            } else {
                gameModel.id to selectedModel.value!!.id
            }

            if (start == end - 1 || start == end - 9) {
                setValuesCrossed(start, end)
                return
            }
            if (GameUtils.canItBeCovered(gameModels.value!!, start, end)) {
                setValuesCrossed(start, end)
            }
        }
    }

    private fun setValuesCrossed(start: Int, end: Int) {
        val startModel = gameModels.value!![start]
        val endModel = gameModels.value!![end]

        updateStacks(ArrayList(undoStack.value!! + NumberRemoval(startModel, endModel)), arrayListOf())

        gameModels.value!![start] = startModel.copy(isCrossed = true)
        gameModels.value!![end] = endModel.copy(isCrossed = true)

        pairNumbers.value = start to end
        removedNumbers.value = removedNumbers.value!! + 2

        gameModelsCount.value = gameModels.value!!.count { !it.isCrossed }

        if (gameModelsCount.value == 0) {
            isGameFinished.value = true
            saveFinishedGameStatistics()
        }
    }

    private fun convertToDisplayableGame(gameDbModel: GameModelDB): MutableList<GameModel> {
        return gameDbModel.gameDigits.mapIndexed { index, c ->
            GameModel.fromMapIndexed(index, c.toString())
        }.toMutableList()
    }

    private fun saveFinishedGameStatistics() {
        viewModelScope.launch {
            statisticsRepository.updateFinishedGameStatistics(
                time = gameTime.value ?: 0L,
                pairs = removedNumbers.value ?: 0
            )
        }
    }

    fun checkCurrentGame() {
        viewModelScope.launch {
            if (isGameFinished.value == true) {
                gameRepository.deleteLastGameFromDatabase()
            } else {
                prepareGameModelToSave()
            }
        }
    }

    private suspend fun prepareGameModelToSave() {
        val gameDbModel = GameModelDB(
            id = 0,
            gameDigits = gameModels.value?.joinToString("") {
                if (it.isCrossed) "0" else it.num.toString()
            } ?: "",
            time = gameTime.value ?: 0L,
            pairCrossed = removedNumbers.value ?: 0
        )
        gameRepository.saveGameToDatabase(gameDbModel)
    }
}