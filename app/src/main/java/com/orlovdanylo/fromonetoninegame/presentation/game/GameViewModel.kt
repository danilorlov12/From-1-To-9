package com.orlovdanylo.fromonetoninegame.presentation.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.orlovdanylo.fromonetoninegame.Repositories
import com.orlovdanylo.fromonetoninegame.presentation.core.BaseViewModel
import com.orlovdanylo.fromonetoninegame.TipController
import com.orlovdanylo.fromonetoninegame.data.game.GameModelDB
import com.orlovdanylo.fromonetoninegame.GameModel
import com.orlovdanylo.fromonetoninegame.presentation.game.models.NumberRemoval
import com.orlovdanylo.fromonetoninegame.presentation.game.undo_redo_operations.IUndoRedoOperation
import com.orlovdanylo.fromonetoninegame.presentation.game.undo_redo_operations.UndoRedoOperation
import com.orlovdanylo.fromonetoninegame.GameController
import com.orlovdanylo.fromonetoninegame.GameMode
import com.orlovdanylo.fromonetoninegame.utils.calculatePosition
import kotlinx.coroutines.launch

class GameViewModel : BaseViewModel(), IUndoRedoOperation by UndoRedoOperation() {

    private val gameRepository = Repositories.gameRepository
    private val statisticsRepository = Repositories.statisticsRepository

    val removedNumbers: MutableLiveData<Int> = MutableLiveData(0)
    val gameModels: MutableLiveData<MutableList<GameModel>> = MutableLiveData()
    val gameModelsCount: MutableLiveData<Int> = MutableLiveData()
    val selectedModel: MutableLiveData<GameModel?> = MutableLiveData()
    val pairNumbers: MutableLiveData<Pair<Int, Int>> = MutableLiveData()

    private var availablePairPos: Int = 0
    val availablePairs: MutableLiveData<List<Pair<Int, Int>>> = MutableLiveData()

    val isGameFinished: MutableLiveData<Boolean> = MutableLiveData(false)
    val startTime: MutableLiveData<Long?> = MutableLiveData()
    val gameTime: MutableLiveData<Long> = MutableLiveData()

    fun initGame(isNewGame: Boolean) {
        viewModelScope.launch {
            if (isNewGame) initializeNewGame() else initializeOldGame()

            gameModelsCount.value = gameModels.value?.count { !it.isCrossed } ?: 0
            updateStacks(arrayListOf(), arrayListOf())
            updateAvailablePairs()
        }
    }

    private suspend fun initializeNewGame() {
        gameRepository.deleteLastGameFromDatabase()
        statisticsRepository.increasePlayedGame()
        gameModels.value = GameMode.Classic().convertToGameModelsList()
        removedNumbers.value = 0
    }

    private suspend fun initializeOldGame() {
        val storedGame = gameRepository.getLastGameFromDatabase()
        removedNumbers.value = storedGame?.pairCrossed ?: 0
        gameModels.value = convertToDisplayableGame(storedGame!!)
    }

    fun initializeGameTime(isNewGame: Boolean) {
        if (isNewGame) {
            startTime.value = 0L
        } else {
            viewModelScope.launch {
                val storedGame = gameRepository.getLastGameFromDatabase()
                startTime.value = storedGame?.time ?: 0L
            }
        }
    }

    fun tap(id: Int) {
        val gameModel = gameModels.value?.find { it.id == id } ?: return

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
        val lastModelId = gameModels.value?.lastOrNull()?.id?.plus(1) ?: return
        val restValues = gameModels.value?.filter { !it.isCrossed } ?: emptyList()

        gameModels.value = (gameModels.value!! + restValues.mapIndexed { index, model ->
            GameModel(index + lastModelId, model.num, false)
        }).toMutableList()

        gameModelsCount.value = gameModels.value!!.count { !it.isCrossed }

        updateStacks(arrayListOf(), arrayListOf())
        updateAvailablePairs()
    }

    private fun checkNumbers(gameModel: GameModel) {
        val controller = GameController(gameModels.value ?: emptyList())
        controller.determineRemovableNumberIds(selectedModel.value!!, gameModel)?.let {
            removeGameModels(it.first, it.second)
        }
    }

    private fun removeGameModels(start: Int, end: Int) {
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
        updateAvailablePairs()
    }

    fun fetchAvailablePair(): Pair<Int, Int>? {
        val availablePairs = availablePairs.value ?: emptyList()
        if (availablePairs.isNotEmpty()) {
            val pair = availablePairs[availablePairPos]
            availablePairPos = availablePairs calculatePosition availablePairPos
            return pair
        }
        return null
    }

    fun updateAvailablePairs() {
        val controller = TipController(gameModels.value ?: emptyList())
        availablePairs.value = controller.fetchAvailablePair()
        availablePairPos = 0
    }

    private fun convertToDisplayableGame(gameDbModel: GameModelDB): MutableList<GameModel> {
        return gameDbModel.gameDigits.mapIndexed { index, c -> GameModel.fromMapIndexed(index, c.toString())
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
                isGameFinished.value = false
            } else {
                prepareStoredGameModel()
            }
        }
        startTime.value = null
    }

    private suspend fun prepareStoredGameModel() {
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