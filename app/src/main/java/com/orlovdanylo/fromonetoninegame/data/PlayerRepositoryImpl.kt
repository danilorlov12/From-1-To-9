package com.orlovdanylo.fromonetoninegame.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.orlovdanylo.fromonetoninegame.domain.PlayerRepository

class PlayerRepositoryImpl(
    private val preferences: SharedPreferences
) : PlayerRepository {

    override fun isFirstLaunch(): Boolean {
        return preferences.getBoolean(FIRST_LAUNCH, true)
    }

    override fun updateFirstLaunch() {
        preferences.edit { putBoolean(FIRST_LAUNCH, false) }
    }

    companion object {
        private const val FIRST_LAUNCH = "first_launch"
    }
}