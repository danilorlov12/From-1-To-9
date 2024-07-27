package com.orlovdanylo.fromonetoninegame.presentation.how_to_play

import com.orlovdanylo.fromonetoninegame.Repositories
import com.orlovdanylo.fromonetoninegame.data.info_pages.InfoPage
import com.orlovdanylo.fromonetoninegame.presentation.core.BaseViewModel

class HowToPlayViewModel : BaseViewModel() {

    private val infoPagesRepository = Repositories.infoPagesRepository

    private val pages = infoPagesRepository.fetchPages()
    var selectedPagePosition: Int = 0

    fun obtainPageInfoByPosition(position: Int): InfoPage? {
        return pages[position]
    }
}