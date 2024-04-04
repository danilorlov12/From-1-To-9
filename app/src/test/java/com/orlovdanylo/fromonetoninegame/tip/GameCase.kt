package com.orlovdanylo.fromonetoninegame.tip

import com.orlovdanylo.fromonetoninegame.GameModel

interface GameCase {
    val numbers: List<String>
    val response: List<Pair<Int, Int>>

    class Case1 : GameCase {
        override val response: List<Pair<Int, Int>> = listOf(Pair(0, 1), Pair(1, 26))

        override val numbers: List<String> = listOf(
            "1", "1", "0", "0", "0", "0", "0", "0", "0",
            "0", "0", "0", "0", "0", "0", "0", "0", "0",
            "0", "0", "0", "0", "0", "0", "0", "0", "1"
        )
    }

    class Case2 : GameCase {
        override val response: List<Pair<Int, Int>> = listOf(Pair(0, 18), Pair(18, 26))

        override val numbers: List<String> = listOf(
            "1", "0", "0", "0", "0", "0", "0", "0", "0",
            "0", "0", "0", "0", "0", "0", "0", "0", "0",
            "1", "0", "0", "0", "0", "0", "0", "0", "1"
        )
    }

    class Case3 : GameCase {
        override val response: List<Pair<Int, Int>> = listOf(Pair(0, 12))

        override val numbers: List<String> = listOf(
            "1", "0", "0", "0", "0", "0", "0", "0", "0",
            "0", "0", "0", "9", "4", "0", "0", "0", "0",
            "0", "0", "1", "0", "0", "0", "0", "0", "0"
        )
    }

    class Case4 : GameCase {
        override val response: List<Pair<Int, Int>> = listOf(
            Pair(3, 21), Pair(4, 13), Pair(13, 20)
        )

        override val numbers: List<String> = listOf(
            "1", "0", "0", "5", "1", "0", "0", "0", "0",
            "0", "0", "0", "0", "1", "0", "0", "0", "0",
            "0", "0", "1", "5", "0", "0", "0", "0", "0"
        )
    }

    class Case5 : GameCase {
        override val response: List<Pair<Int, Int>> = listOf()

        override val numbers: List<String> = listOf(
            "5", "3", "6", "5", "1", "0", "0", "7", "0",
            "4", "2", "1", "8", "6", "0", "5", "8", "0",
            "3", "1", "4", "5", "0", "0", "0", "0", "0"
        )
    }

    class Case6 : GameCase {
        override val response: List<Pair<Int, Int>> = listOf(Pair(4, 13))

        override val numbers: List<String> = listOf(
            "5", "3", "6", "5", "1", "0", "0", "7", "0",
            "4", "2", "1", "8", "9", "0", "5", "8", "0",
            "3", "1", "4", "5", "0", "0", "0", "0", "0"
        )
    }

    class Case7 : GameCase {
        override val response: List<Pair<Int, Int>> = listOf()

        override val numbers: List<String> = listOf(
            "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "4", "5", "2", "3", "4", "5", "6", "0", "2",
            "1", "2", "0", "4", "5", "1", "2", "3", "4",
            "0", "0", "3", "0", "9", "2", "4", "0", "0",
            "2", "3", "4", "9", "2", "3", "2", "1", "3",
            "9", "8", "0", "3", "4", "5", "1", "3", "4",
            "0", "1", "3", "4", "1", "2", "7", "4", "5"
        )
    }

    class Case8 : GameCase {
        override val response: List<Pair<Int, Int>> = listOf(
            Pair(1, 10), Pair(11, 20), Pair(17, 26), Pair(26, 27), Pair(29, 38), Pair(36, 37),
            Pair(37, 46), Pair(43, 61), Pair(53, 62)
        )

        override val numbers: List<String> = listOf(
            "8", "3", "6", "2", "5", "1", "0", "4", "9", // 00 01 02 03 04 05 06 07 08
            "5", "7", "1", "0", "6", "2", "9", "3", "4", // 09 10 11 12 13 14 15 16 17
            "8", "0", "1", "4", "3", "5", "7", "2", "6", // 18 19 20 21 22 23 24 25 26
            "4", "8", "7", "5", "2", "3", "1", "6", "0", // 27 28 29 30 31 32 33 34 35
            "9", "1", "3", "8", "7", "0", "4", "5", "2", // 36 37 38 39 40 41 42 43 44
            "6", "9", "4", "7", "8", "5", "1", "0", "3", // 45 46 47 48 49 50 51 52 53
            "2", "3", "8", "6", "0", "1", "4", "5", "7"  // 54 55 56 57 58 59 60 61 62
        )
    }

    class Case9 : GameCase {
        override val response: List<Pair<Int, Int>> = listOf(
            Pair(0, 1), Pair(3, 4), Pair(12, 21), Pair(13, 31), Pair(15, 16), Pair(16, 25),
            Pair(21, 30), Pair(24, 33), Pair(25, 34), Pair(26, 28), Pair(29, 30), Pair(31, 40),
            Pair(32, 33), Pair(34, 35), Pair(34, 43), Pair(36, 45), Pair(37, 38), Pair(41, 50),
            Pair(45, 46), Pair(49, 58), Pair(52, 61)
        )

        override val numbers: List<String> = listOf(
            "2", "8", "7", "6", "4", "9", "5", "3", "0", // 00 01 02 03 04 05 06 07 08
            "1", "4", "2", "3", "5", "6", "1", "9", "0", // 09 10 11 12 13 14 15 16 17
            "7", "8", "4", "3", "0", "9", "2", "1", "6", // 18 19 20 21 22 23 24 25 26
            "0", "4", "3", "7", "5", "8", "2", "1", "9", // 27 28 29 30 31 32 33 34 35
            "6", "8", "2", "0", "5", "9", "7", "1", "3", // 36 37 38 39 40 41 42 43 44
            "4", "6", "1", "8", "7", "9", "2", "3", "5", // 45 46 47 48 49 50 51 52 53
            "0", "1", "2", "4", "3", "5", "6", "7", "8"  // 54 55 56 57 58 59 60 61 62
        )
    }

    class Case10 : GameCase {
        override val response: List<Pair<Int, Int>> = listOf()

        override val numbers: List<String> = listOf(
            "2", "4", "2", "3", "1", "4", "0", "9", "2",
            "1", "2", "5", "0", "3", "5", "4", "3", "6",
            "0", "3", "6", "8", "4", "3", "2", "6", "3",
            "2", "1", "0", "3", "5", "0", "3", "5", "2",
            "9", "8", "3", "4", "3", "1", "2", "1", "3",
            "0", "5", "4", "5", "2", "0", "7", "4", "5",
            "0", "1", "2", "3", "1", "0", "4", "2", "9",
            "2", "3", "1", "4", "3", "4", "2", "4", "2",
            "0", "1", "2", "5", "4", "5", "6", "7", "9",
            "7", "0", "1", "4", "3", "1", "7", "2", "6",
            "2", "3", "4", "9", "2", "3", "2", "1", "3",
            "4", "2", "1", "8", "6", "0", "5", "8", "0"
        )
    }

    fun convertToGameModels(): List<GameModel> {
        return numbers.mapIndexed { index, s ->
            GameModel(index, s.toInt(), s == "0")
        }.toMutableList()
    }
}