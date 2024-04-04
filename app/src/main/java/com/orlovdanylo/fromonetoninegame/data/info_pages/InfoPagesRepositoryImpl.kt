package com.orlovdanylo.fromonetoninegame.data.info_pages

import com.orlovdanylo.fromonetoninegame.R
import com.orlovdanylo.fromonetoninegame.domain.InfoPagesRepository
import com.orlovdanylo.fromonetoninegame.GameModel

class InfoPagesRepositoryImpl : InfoPagesRepository {

    private val secondPageInfo = listOf(
        "0", "0", "0", "0", "0",
        "2", "8", "0", "1", "1",
        "0", "0", "0", "0", "0"
    )

    private val thirdPageInfo = listOf(
        "6", "0", "0", "0", "0",
        "6", "0", "1", "9", "0",
        "0", "0", "0", "0", "0"
    )

    private val fourthPageInfo = listOf(
        "5", "0", "1", "0", "9",
        "0", "0", "0", "0", "0",
        "5", "0", "0", "0", "0",
    )

    private val fifthPageInfo = listOf(
        "0", "0", "0", "7", "0",
        "0", "3", "0", "0", "0",
        "0", "0", "0", "0", "0"
    )

    override fun fetchPages(): HashMap<Int, InfoPage> = HashMap<Int, InfoPage>().apply {
        put(
            0, InfoPage(
                R.string.purpose_of_the_game,
                emptyList()
            )
        )
        put(
            1, InfoPage(
                R.string.tap_on_pair,
                convertToModelList(secondPageInfo)
            )
        )
        put(
            2, InfoPage(
                R.string.remove_vertically_horizontally,
                convertToModelList(thirdPageInfo)
            )
        )
        put(
            3, InfoPage(
                R.string.remove_in_between,
                convertToModelList(fourthPageInfo)
            )
        )
        put(
            4, InfoPage(
                R.string.remove_last_first,
                convertToModelList(fifthPageInfo)
            )
        )
    }

    private fun convertToModelList(list: List<String>): List<GameModel> {
        return list.mapIndexed { index, num ->
            GameModel(index, num.toInt(), num == "0")
        }
    }
}