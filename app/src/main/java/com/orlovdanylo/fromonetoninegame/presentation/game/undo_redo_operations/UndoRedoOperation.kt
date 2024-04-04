package com.orlovdanylo.fromonetoninegame.presentation.game.undo_redo_operations

import androidx.lifecycle.MutableLiveData
import com.orlovdanylo.fromonetoninegame.GameModel
import com.orlovdanylo.fromonetoninegame.presentation.game.models.NumberRemoval

class UndoRedoOperation : IUndoRedoOperation {

    override val updatedPair: MutableLiveData<Pair<Int, Int>> = MutableLiveData()

    override val undoStack: MutableLiveData<MutableList<NumberRemoval>> =
        MutableLiveData(arrayListOf())

    override val redoStack: MutableLiveData<MutableList<NumberRemoval>> =
        MutableLiveData(arrayListOf())

    override fun undo(gameModels: MutableList<GameModel>, deletedPairs: MutableLiveData<Int>) {
        val lastNumberRemoval = undoStack.value!!.last()
        undoStack.value = undoStack.value!!.subList(0, undoStack.value!!.size - 1)

        val firstModel = lastNumberRemoval.number1
        val secondModel = lastNumberRemoval.number2

        gameModels[firstModel.id] = firstModel.copy(isSelected = false)
        gameModels[secondModel.id] = secondModel.copy(isSelected = false)

        redoStack.value = ArrayList(
            redoStack.value!! + NumberRemoval(
                lastNumberRemoval.number1.copy(isCrossed = false),
                lastNumberRemoval.number2.copy(isCrossed = false)
            )
        )
        updatedPair.value = Pair(firstModel.id, secondModel.id)

        deletedPairs.value = deletedPairs.value!! - 2
    }

    override fun redo(gameModels: MutableList<GameModel>, deletedPairs: MutableLiveData<Int>) {
        val lastNumberCanceled = redoStack.value!!.last()
        redoStack.value = redoStack.value!!.subList(0, redoStack.value!!.size - 1)

        val firstModel = lastNumberCanceled.number1
        val secondModel = lastNumberCanceled.number2

        gameModels[firstModel.id] = firstModel.copy(isCrossed = true, isSelected = false)
        gameModels[secondModel.id] = secondModel.copy(isCrossed = true, isSelected = false)

        undoStack.value = ArrayList(undoStack.value!! + lastNumberCanceled)

        updatedPair.value = Pair(firstModel.id, secondModel.id)

        deletedPairs.value = deletedPairs.value!! + 2
    }

    override fun updateStacks(
        undoStack: MutableList<NumberRemoval>,
        redoStack: MutableList<NumberRemoval>
    ) {
        this.undoStack.value = undoStack
        this.redoStack.value = redoStack
    }
}