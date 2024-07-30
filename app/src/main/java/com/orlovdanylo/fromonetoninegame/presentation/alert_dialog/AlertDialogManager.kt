package com.orlovdanylo.fromonetoninegame.presentation.alert_dialog

import android.content.Context
import com.orlovdanylo.fromonetoninegame.R

class AlertDialogManager(private val context: Context) {

    fun showCongratulations(action: () -> Unit) {
        CustomAlertDialog(
            context = context,
            title = context.getString(R.string.congratulations_you_won),
            buttons = listOf(ButtonSetting(R.string.okay, action))
        ).create()
    }

    fun showGameModes(actionClassic: () -> Unit, actionRandom: () -> Unit) {
        CustomAlertDialog(
            context = context,
            title = context.getString(R.string.choose_game_mode),
            buttons = listOf(
                ButtonSetting(R.string.classic, actionClassic),
                ButtonSetting(R.string.random, actionRandom)
            )
        ).create()
    }
}