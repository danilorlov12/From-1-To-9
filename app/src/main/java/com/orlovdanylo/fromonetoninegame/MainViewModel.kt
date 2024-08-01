package com.orlovdanylo.fromonetoninegame

import com.orlovdanylo.fromonetoninegame.presentation.core.BaseViewModel

class MainViewModel : BaseViewModel() {

    private val playerRepository = Repositories.playerRepository

    fun isFirstLaunch(): Boolean {
        return playerRepository.isFirstLaunch()
    }
}