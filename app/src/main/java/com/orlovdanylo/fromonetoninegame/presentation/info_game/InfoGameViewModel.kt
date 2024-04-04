package com.orlovdanylo.fromonetoninegame.presentation.info_game

import androidx.lifecycle.MutableLiveData
import com.orlovdanylo.fromonetoninegame.Repositories
import com.orlovdanylo.fromonetoninegame.presentation.core.BaseViewModel
import com.orlovdanylo.fromonetoninegame.data.info_pages.InfoPage

class InfoGameViewModel : BaseViewModel() {

    private val infoPagesRepository = Repositories.infoPagesRepository

    private val pages = infoPagesRepository.fetchPages()
    val currentPage: MutableLiveData<InfoPage?> = MutableLiveData()

    fun initFirstPage() {
        currentPage.value = pages.getValue(0)
    }

    fun nextPage() {
        val currentPageKey = findCurrentPageKey()
        currentPage.value = if (currentPageKey == 4) null else pages.getValue(findCurrentPageKey() + 1)
    }

    fun previousPage() {
        val currentPageKey = findCurrentPageKey()
        currentPage.value = if (currentPageKey == 0) null else pages.getValue(findCurrentPageKey() - 1)
    }

    private fun findCurrentPageKey(): Int {
        return pages.entries.find { currentPage.value == it.value }?.key ?: 0
    }
}
