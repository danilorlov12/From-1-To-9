package com.orlovdanylo.fromonetoninegame

enum class GameMode(val id: Int) {
    CLASSIC(0), RANDOM(1), FAST_WIN(-1), UNKNOWN(-2);

    companion object {
        fun playableGameModes(): List<GameMode> {
            return listOf(CLASSIC, RANDOM)
        }

        fun gameModeById(id: Int): GameMode {
            return entries.find { it.id == id } ?: UNKNOWN
        }
    }
}