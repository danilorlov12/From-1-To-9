package com.orlovdanylo.fromonetoninegame.data.info_pages

import com.orlovdanylo.fromonetoninegame.GameModel

data class InfoPage(
    val descriptionResId: Int,
    val listOfModels: List<GameModel>
)