package com.orlovdanylo.fromonetoninegame.presentation.how_to_play

import androidx.lifecycle.MutableLiveData
import com.orlovdanylo.fromonetoninegame.Repositories
import com.orlovdanylo.fromonetoninegame.data.info_pages.InfoPage
import com.orlovdanylo.fromonetoninegame.presentation.core.BaseViewModel

class HowToPlayViewModel : BaseViewModel() {

    private val infoPagesRepository = Repositories.infoPagesRepository
    private val playerRepository = Repositories.playerRepository

    private var firstLaunch: Boolean = true

    private val pages = infoPagesRepository.fetchPages()
    val selectedPagePosition = MutableLiveData(0)

    init {
        firstLaunch = playerRepository.isFirstLaunch()
    }

    fun obtainPageInfoByPosition(position: Int): InfoPage? {
        return pages[position]
    }

    fun hideBackButtonOnFirstPage(): Boolean {
        return firstLaunch && selectedPagePosition.value == 0
    }

    fun updateFirstLaunch() {
        playerRepository.updateFirstLaunch()
    }
}