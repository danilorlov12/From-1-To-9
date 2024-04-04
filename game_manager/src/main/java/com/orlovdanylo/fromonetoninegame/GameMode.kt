package com.orlovdanylo.fromonetoninegame

interface GameMode {
    val numbers: List<String>

    class Classic : GameMode {
        override val numbers: List<String> = listOf(
            "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "1", "1", "1", "2", "1", "3", "1", "4", "1",
            "5", "1", "6", "1", "7", "1", "8", "1", "9"
        )
    }

    class FastWin : GameMode {
        override val numbers: List<String> = listOf(
            "1", "1", "1", "1", "1", "1", "1", "1", "9",
            "1", "1", "1", "1", "1", "1", "1", "1", "1",
            "1", "1", "1", "1", "1", "1", "1", "1", "9"
        )
    }

    fun convertToGameModelsList(): MutableList<GameModel> {
        return numbers.mapIndexed { index, s ->
            GameModel(index, s.toInt(), false)
        }.toMutableList()
    }
}