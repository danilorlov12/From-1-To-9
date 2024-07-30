package com.orlovdanylo.fromonetoninegame

class GameModelsProvider(private val mode: GameMode) {

    fun obtainGameModels(): MutableList<GameModel> {
        val numbers = obtainNumbers()
        return numbers.mapIndexed { index, s ->
            GameModel(index, s, false)
        }.toMutableList()
    }

    private fun obtainNumbers() = when (mode) {
        GameMode.CLASSIC -> {
            listOf(
                1, 2, 3, 4, 5, 6, 7, 8, 9,
                1, 1, 1, 2, 1, 3, 1, 4, 1,
                5, 1, 6, 1, 7, 1, 8, 1, 9
            )
        }
        GameMode.RANDOM -> {
            IntArray(27) {
                kotlin.random.Random.nextInt(1, 10)
            }.toList()
        }
        GameMode.FAST_WIN -> {
            listOf(
                1, 1, 1, 1, 1, 1, 1, 1, 9,
                1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 9
            )
        }
        GameMode.UNKNOWN -> listOf()
    }
}