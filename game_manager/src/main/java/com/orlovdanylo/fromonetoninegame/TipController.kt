package com.orlovdanylo.fromonetoninegame

class TipController(
    private val models: List<GameModel>
) {

    fun fetchAvailablePair(): List<Pair<Int, Int>> {
        val gameController = GameController(models)
        val uncrossed = models.filter { !it.isCrossed }

        return uncrossed.flatMapIndexed { index, model ->
            val nextInRow = uncrossed.getOrNull(index + 1)
            val nextInColumn = (model.id + 9 until models.size step 9)
                .map { models[it] }
                .firstOrNull { !it.isCrossed }

            listOfNotNull(nextInRow, nextInColumn)
                .distinctBy { it.id }
                .sortedBy { it.id }
                .mapNotNull { gameController.determineRemovableNumberIds(model, it) }
        }
    }
}