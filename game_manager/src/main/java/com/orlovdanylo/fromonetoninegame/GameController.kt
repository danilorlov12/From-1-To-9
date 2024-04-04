package com.orlovdanylo.fromonetoninegame

class GameController(
    private val gameModels: List<GameModel>
) {

    fun determineRemovableNumberIds(model1: GameModel, model2: GameModel): Pair<Int, Int>? {
        if (checkNumbers(model1.num, model2.num)) {
            val (start, end) = if (model1.id < model2.id) {
                model1.id to model2.id
            } else {
                model2.id to model1.id
            }

            if (start == end - 1 || start == end - 9) {
                return Pair(start, end)
            }

            if (canItBeCovered(gameModels, start, end)) {
                return Pair(start, end)
            }
        }
        return null
    }

    private fun checkNumbers(firstValue: Int, secondValue: Int): Boolean {
        return firstValue + secondValue == 10 || firstValue == secondValue
    }

    private fun canItBeCovered(models: List<GameModel>, start: Int, end: Int): Boolean {
        return canItBeCoveredByRow(models, start, end) || canItBeCoveredByColumn(models, start, end)
    }

    private fun canItBeCoveredByRow(models: List<GameModel>, start: Int, end: Int): Boolean {
        for (i in start + 1 until end) {
            if (!models[i].isCrossed) return false
        }
        return true
    }

    private fun canItBeCoveredByColumn(models: List<GameModel>, start: Int, end: Int): Boolean {
        if ((end - start) % 9 != 0) return false

        for (i in start + 9 until end step 9) {
            if (!models[i].isCrossed && i != end) return false
        }
        return true
    }
}