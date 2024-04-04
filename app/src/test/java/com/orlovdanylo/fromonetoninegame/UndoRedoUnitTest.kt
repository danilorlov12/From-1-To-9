package com.orlovdanylo.fromonetoninegame

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.orlovdanylo.fromonetoninegame.presentation.game.GameViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UndoRedoUnitTest {

    private lateinit var viewModel: GameViewModel
    private lateinit var startModels: List<GameModel>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainDispatcherRule()

    @Before
    fun before() {
        viewModel = GameViewModel()

        startModels = GameMode.Classic().convertToGameModelsList()

        viewModel.initGame(true)
    }

    @Test
    fun test_one_model_undo() {

        // 1 tap
        viewModel.tap(0)
        viewModel.tap(9)

        assertEquals(true, viewModel.gameModels.value!![0].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![9].isCrossed)

        /*
            # 2 3 4 5 6 7 8 9   |   00 01 02 03 04 05 06 07 08
            # 1 1 2 1 3 1 4 1   |   09 10 11 12 13 14 15 16 17
            5 1 6 1 7 1 8 1 9   |   18 19 20 21 22 23 24 25 26
         */

        val lastRemoval = viewModel.undoStack.value!!.last()
        assertEquals(viewModel.gameModels.value!![0].copy(isCrossed = false),
            lastRemoval.number1)

        assertEquals(viewModel.gameModels.value!![9].copy(isCrossed = false),
            lastRemoval.number2)

        viewModel.undo(viewModel.gameModels.value!!, MutableLiveData())
        assertEquals(false, viewModel.gameModels.value!![0].isCrossed)
        assertEquals(false, viewModel.gameModels.value!![9].isCrossed)

        /*
            1 2 3 4 5 6 7 8 9   |   00 01 02 03 04 05 06 07 08
            1 1 1 2 1 3 1 4 1   |   09 10 11 12 13 14 15 16 17
            5 1 6 1 7 1 8 1 9   |   18 19 20 21 22 23 24 25 26
         */
    }

    @Test
    fun test_one_model_undo_and_redo() {
        test_one_model_undo()

        val lastCanceled = viewModel.redoStack.value!!.last()
        assertEquals(viewModel.gameModels.value!![0], lastCanceled.number1)
        assertEquals(viewModel.gameModels.value!![9], lastCanceled.number2)

        viewModel.redo(viewModel.gameModels.value!!, MutableLiveData())

        assertEquals(true, viewModel.gameModels.value!![0].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![9].isCrossed)

        /*
            # 2 3 4 5 6 7 8 9   |   00 01 02 03 04 05 06 07 08
            # 1 1 2 1 3 1 4 1   |   09 10 11 12 13 14 15 16 17
            5 1 6 1 7 1 8 1 9   |   18 19 20 21 22 23 24 25 26
         */
    }

    @Test
    fun test_two_models_undo_and_redo() {

        viewModel.tap(8)
        viewModel.tap(9)
        viewModel.tap(17)
        viewModel.tap(26)

        assertEquals(true, viewModel.gameModels.value!![8].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![9].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![17].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![26].isCrossed)

        /*
            1 2 3 4 5 6 7 8 #   |   00 01 02 03 04 05 06 07 08
            # 1 1 2 1 3 1 4 #   |   09 10 11 12 13 14 15 16 17
            5 1 6 1 7 1 8 1 #   |   18 19 20 21 22 23 24 25 26
         */

        val lastRemoval = viewModel.undoStack.value!!.last()
        assertEquals(viewModel.gameModels.value!![17].copy(isCrossed = false),
            lastRemoval.number1)

        assertEquals(viewModel.gameModels.value!![26].copy(isCrossed = false),
            lastRemoval.number2)

        viewModel.undo(viewModel.gameModels.value!!, MutableLiveData())
        assertEquals(false, viewModel.gameModels.value!![17].isCrossed)
        assertEquals(false, viewModel.gameModels.value!![26].isCrossed)

        viewModel.undo(viewModel.gameModels.value!!, MutableLiveData())
        assertEquals(false, viewModel.gameModels.value!![8].isCrossed)
        assertEquals(false, viewModel.gameModels.value!![9].isCrossed)

        viewModel.redo(viewModel.gameModels.value!!, MutableLiveData())
        assertEquals(true, viewModel.gameModels.value!![8].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![9].isCrossed)

        viewModel.redo(viewModel.gameModels.value!!, MutableLiveData())
        assertEquals(true, viewModel.gameModels.value!![17].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![26].isCrossed)
    }

    @Test
    fun test_after_numbers_was_crossed() {

        viewModel.tap(8)
        viewModel.tap(9)
        viewModel.tap(17)
        viewModel.tap(26)

        assertEquals(true, viewModel.gameModels.value!![8].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![9].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![17].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![26].isCrossed)

        /*
            1 2 3 4 5 6 7 8 #   |   00 01 02 03 04 05 06 07 08
            # 1 1 2 1 3 1 4 #   |   09 10 11 12 13 14 15 16 17
            5 1 6 1 7 1 8 1 #   |   18 19 20 21 22 23 24 25 26
         */

        viewModel.undo(viewModel.gameModels.value!!, MutableLiveData())
        assertEquals(false, viewModel.gameModels.value!![17].isCrossed)
        assertEquals(false, viewModel.gameModels.value!![26].isCrossed)

        /*
            1 2 3 4 5 6 7 8 #   |   00 01 02 03 04 05 06 07 08
            # 1 1 2 1 3 1 4 1   |   09 10 11 12 13 14 15 16 17
            5 1 6 1 7 1 8 1 9   |   18 19 20 21 22 23 24 25 26
         */

        viewModel.tap(10)
        viewModel.tap(19)
        assertEquals(true, viewModel.gameModels.value!![10].isCrossed)
        assertEquals(true, viewModel.gameModels.value!![19].isCrossed)

        /*
            1 2 3 4 5 6 7 8 #   |   00 01 02 03 04 05 06 07 08
            # # 1 2 1 3 1 4 1   |   09 10 11 12 13 14 15 16 17
            5 # 6 1 7 1 8 1 9   |   18 19 20 21 22 23 24 25 26
         */

        val lastRemoval = viewModel.redoStack.value!!.lastOrNull()
        assertEquals(null, lastRemoval)
    }
}