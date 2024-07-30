package com.orlovdanylo.fromonetoninegame.presentation.game.models

import com.orlovdanylo.fromonetoninegame.GameMode
import java.io.Serializable

data class GameSettingsBundle(
    val isNewGame: Boolean,
    val mode: GameMode
): Serializable