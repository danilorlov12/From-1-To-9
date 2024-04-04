package com.orlovdanylo.fromonetoninegame

data class GameModel(
    val id: Int,
    val num: Int,
    val isCrossed: Boolean,
    var isSelected: Boolean = false
) {
    companion object {
        fun fromMapIndexed(index: Int, char: String) = GameModel(
            id = index,
            num = char.toInt(),
            isCrossed = char.toInt() == 0,
            isSelected = false
        )
    }
}
