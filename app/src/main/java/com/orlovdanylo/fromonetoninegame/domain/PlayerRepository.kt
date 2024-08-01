package com.orlovdanylo.fromonetoninegame.domain

interface PlayerRepository {
    fun isFirstLaunch(): Boolean
    fun updateFirstLaunch()
}