package com.orlovdanylo.fromonetoninegame.presentation.game.undo_redo_operations

import androidx.lifecycle.MutableLiveData
import com.orlovdanylo.fromonetoninegame.GameModel
import com.orlovdanylo.fromonetoninegame.presentation.game.models.NumberRemoval

interface IUndoRedoOperation {
    val updatedPair: MutableLiveData<Pair<Int, Int>>
    val undoStack: MutableLiveData<MutableList<NumberRemoval>>
    val redoStack: MutableLiveData<MutableList<NumberRemoval>>

    fun undo(gameModels: MutableList<GameModel>, deletedPairs: MutableLiveData<Int>)
    fun redo(gameModels: MutableList<GameModel>, deletedPairs: MutableLiveData<Int>)
    fun updateStacks(undoStack: MutableList<NumberRemoval>, redoStack: MutableList<NumberRemoval>)
}