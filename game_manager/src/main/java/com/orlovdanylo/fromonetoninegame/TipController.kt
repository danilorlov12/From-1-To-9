package com.orlovdanylo.fromonetoninegame

class TipController(
    private val models: List<GameModel>
) {

    fun fetchAvailablePair(): List<Pair<Int, Int>> {
        val gameController = GameController(models)
        val visitedIds = mutableSetOf<Int>()
        val pairs: MutableList<Pair<Int, Int>> = mutableListOf()

        val uncrossedModels = models.filter { !it.isCrossed }
        uncrossedModels.forEachIndexed { index, model ->
            visitedIds.add(model.id)
            uncrossedModels.subList(index + 1, uncrossedModels.size).filter { it.id !in visitedIds }.find { secondModel ->
                gameController.determineRemovableNumberIds(model, secondModel)?.let {
                    pairs.add(Pair(it.first, it.second))
                }
                false
            }
        }
        return pairs
    }
}